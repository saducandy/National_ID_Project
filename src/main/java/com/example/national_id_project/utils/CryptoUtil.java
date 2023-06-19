package com.example.national_id_project.utils;

import com.example.national_id_project.ApiConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.jose4j.jws.JsonWebSignature;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.MGF1ParameterSpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;


public class CryptoUtil {
    private static SecretKey secretKey;
    public static String sign(ApiConfig apiConfig, String jsonString) throws Exception {

        JsonWebSignature jwSign = new JsonWebSignature();
        KeyStore.PrivateKeyEntry keyEntry = loadP12(apiConfig);

        if (Objects.isNull(keyEntry)) {
            throw new KeyStoreException("Key file not available for partner type");
        }

        PrivateKey privateKey = keyEntry.getPrivateKey();

        X509Certificate x509Certificate = null;

        if(x509Certificate == null) {
            x509Certificate = (X509Certificate) keyEntry.getCertificate();
        }

        jwSign.setCertificateChainHeaderValue(new X509Certificate[] { x509Certificate });
        jwSign.setPayload(jsonString);
        jwSign.setAlgorithmHeaderValue("RS256");
        jwSign.setKey(privateKey);
        jwSign.setDoKeyValidation(false);

        return jwSign.getCompactSerialization();

    }

    public static KeyStore.PrivateKeyEntry loadP12(ApiConfig apiConfig) throws Exception {
        KeyStore.PrivateKeyEntry privateKeyEntry = null;
        String cyptoPassword=apiConfig.getP12password();
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream in = new FileInputStream(apiConfig.getP12Path());//getClass().getClassLoader().getResourceAsStream(fileName);
            keyStore.load(in, cyptoPassword.toCharArray());
            KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection(cyptoPassword.toCharArray());
            privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(apiConfig.getP12password() ,password);
        }catch (Exception e) {
            System.out.println("Not able to decrypt the data :"+e.getMessage());
        }
        return privateKeyEntry;
    }


    public static X509Certificate getCertificate(ApiConfig apiConfig) throws Exception {

        X509Certificate cert = null;
        InputStream inStream = new FileInputStream(apiConfig.getFaydaCertPath());
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        cert = (X509Certificate) cf.generateCertificate(inStream);
        inStream.close();
        return cert;
    }


    public static String generateHMAC(String requestJSON) throws Exception {
        System.out.println("HMAC TO BE GENERATED FOR > "+requestJSON);

        byte[] hashbytes = generateHash(requestJSON.getBytes());

        String hash = bytesToHex(hashbytes);
        System.out.println("HMAC BEFORE ENCRYPTION > "+ hash);
        return doEncode(symmetricEncrypt(secretKey, hash.getBytes(), null));
    }


    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray(); //Why java WHYYYYYY
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] generateHash(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(bytes);
    }



    public static byte[] generateThumbprint(X509Certificate cert) throws Exception {
        byte[] digest = DigestUtils.sha256(cert.getEncoded());

        System.out.println("Thumbprint in hex > "+bytesToHex(digest));

        return digest;
    }



    public static SecretKey generateSecretKey() throws Exception {

        SecureRandom rand = new SecureRandom();
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, rand);
        SecretKey skey = keyGen.generateKey();
        secretKey = skey;
        return secretKey;

    }

    public static String encryptSecretKeyAsymmetric(byte[] key, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");

        final OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
                PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);
        byte[] cipherText = cipher.doFinal(key);
//        System.out.println(cipherText.length);
        String encodedCipher =  doEncode(cipherText);
//        System.out.println(encodedCipher);
        return encodedCipher;
    }



    public static String encryptAESGCMNOPadding(String data) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/GCM/NOPadding");// Because we can't have AES/GCM/PKCS5Padding thank you no thank you
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        byte[] ivBytes = generateIV(16);// new byte[16];

        String reqString=data;

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes); // Of course java would do this.

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        byte[] planByte=reqString.getBytes();
        byte[] encryptedBytes = cipher.doFinal(planByte);
        byte[] output = null;
        output = new byte[cipher.getOutputSize(planByte.length) + cipher.getBlockSize()];


        System.arraycopy(encryptedBytes, 0, output, 0, encryptedBytes.length);
        System.arraycopy(ivBytes, 0, output, encryptedBytes.length, ivBytes.length);

        return doEncode(output);
    }



    public static byte[]  symmetricEncrypt(SecretKey key, byte[] data, byte[] aad) {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/GCM/NOPadding");
        } catch (Exception e) {

        }
        byte[] output = null;
        System.out.println("BLOCK SIZE " + cipher.getBlockSize());
        byte[] randomIV = generateIV(cipher.getBlockSize());
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, randomIV);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
            output = new byte[cipher.getOutputSize(data.length) + cipher.getBlockSize()];
            if (aad != null && aad.length != 0) {
                cipher.updateAAD(aad);
            }
            byte[] encryptedData = doFinal(data, cipher);
            System.arraycopy(encryptedData, 0, output, 0, encryptedData.length);
            System.arraycopy(randomIV, 0, output, encryptedData.length, randomIV.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private static byte[] doFinal(byte[] data, Cipher cipher) throws Exception {
        return cipher.doFinal(data);
    }


    public static String doEncode(byte[] encryptedBytes) {
        return Base64.encodeBase64URLSafeString(encryptedBytes);
    }

    public static byte[] doDecode(String data) {
        System.out.println(Base64.decodeBase64(data));
        return Base64.decodeBase64(data);
    }

    public static byte[]  generateIV(int blockSize) {
        byte[] byteIV = new byte[blockSize];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(byteIV);
        return byteIV;
    }

    

    public static String symmetricDecryptResponse(ApiConfig apiConfig, String cipherText) throws Exception {

        byte[][] splitData = splitAtFirstOccurrence(doDecode(cipherText), apiConfig.getSplitter().getBytes());
        Map<String, String> responsePair = Map.of("encryptedKey", doEncode(splitData[0]),
                "encryptedMessage", doEncode(splitData[1]));
        System.out.println("Key: "+responsePair.get("encryptedKey"));
        System.out.println("Message: "+responsePair.get("encryptedMessage"));
        byte[] decryptedKey = decryptSecretKey(loadP12(apiConfig).getPrivateKey(), splitData[0]);
        SecretKey secretKey =  new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
        byte[] identityBlock = decryptIdentityBlock(splitData[1], secretKey);

        ObjectMapper mapper = new ObjectMapper();
        Object objectString=  mapper.readValue(identityBlock, Object.class);
        String ekycResponseText = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectString);
        System.out.println(ekycResponseText);
        return ekycResponseText;
    }

    private static byte[][] splitAtFirstOccurrence(byte[] strBytes, byte[] sepBytes) {
        int index = findIndex(strBytes, sepBytes);
        if (index >= 0) {
            byte[] bytes1 = new byte[index];
            byte[] bytes2 = new byte[strBytes.length - (bytes1.length + sepBytes.length)];
            System.arraycopy(strBytes, 0, bytes1, 0, bytes1.length);
            System.arraycopy(strBytes, (bytes1.length + sepBytes.length), bytes2, 0, bytes2.length);
            return new byte[][] { bytes1, bytes2 };
        } else {
            return new byte[][] { strBytes, new byte[0] };
        }
    }
    private static int findIndex(byte[] arr, byte[] subarr) {
        int len = arr.length;
        int subArrayLen = subarr.length;
        return IntStream.range(0, len).filter(currentIndex -> {
                    if ((currentIndex + subArrayLen) <= len) {
                        byte[] sArray = new byte[subArrayLen];
                        System.arraycopy(arr, currentIndex, sArray, 0, subArrayLen);
                        return Arrays.equals(sArray, subarr);
                    }
                    return false;
                }).findFirst() // first occurence
                .orElse(-1); // No element found
    }

    private static byte[] decryptSecretKey(PrivateKey privKey, byte[] encKey)  {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.DECRYPT_MODE, privKey, oaepParams);
            return cipher.doFinal(encKey, 0, encKey.length);
        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }

    private static byte[] decryptIdentityBlock(byte[] message, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("AES/GCM/NOPadding"); //NoPadding

        byte[] nonce = Arrays.copyOfRange(message, message.length - cipher.getBlockSize(), message.length);
        byte[] encryptedKycData = Arrays.copyOf(message, message.length - cipher.getBlockSize());

//        SecretKey secretKey =  new SecretKeySpec(key, 0, decSecKey.length, "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);

        byte[] decryptedValue=cipher.doFinal(encryptedKycData);
        return decryptedValue;

    }

    public static byte[] symmetricDecrypt(SecretKeySpec secretKeySpec, byte[] dataBytes, byte[] ivBytes, byte[] aadBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NOPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            cipher.updateAAD(aadBytes);
            return cipher.doFinal(dataBytes);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getTime(){
        return formatToISO(getUTCTime());
    }

    private static String formatToISO(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    private static  LocalDateTime getUTCTime(){
        return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
    }



}
