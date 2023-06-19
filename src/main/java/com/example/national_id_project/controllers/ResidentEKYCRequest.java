package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.DTOs.ResidentAuthDTO;
import com.example.national_id_project.utils.CryptoUtil;
import com.example.national_id_project.utils.RequestUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.codec.binary.Base64;

public class ResidentEKYCRequest {
    public static String getEkycForResident(ApiConfig apiConfig, String authToken, String otp, String transactionId, String individualId, String idType) {
        try {
            ResidentAuthDTO residentAuthDTO = new ResidentAuthDTO();
            residentAuthDTO.setId("fayda.identity.kyc");
            residentAuthDTO.setVersion(apiConfig.getVersion());
            residentAuthDTO.setRequestTime(CryptoUtil.getTime());
            residentAuthDTO.setEnv(apiConfig.getEnv());
            residentAuthDTO.setDomainUri(apiConfig.getBaseURL());
            residentAuthDTO.setTransactionID(transactionId);
            residentAuthDTO.setConsentObtained(true);
            residentAuthDTO.setIndividualId(individualId);
            residentAuthDTO.setIndividualIdType(idType);
            residentAuthDTO.setRequestSessionKey(CryptoUtil.encryptSecretKeyAsymmetric(CryptoUtil.generateSecretKey().getEncoded(),
                    CryptoUtil.getCertificate(apiConfig).getPublicKey()));

            JsonObject request = new JsonObject();
            JsonObject requestedAuth = new JsonObject();
            requestedAuth.addProperty("otp", true);
            requestedAuth.addProperty("demo", false);
            requestedAuth.addProperty("bio", false);

            request.addProperty("timestamp", residentAuthDTO.getRequestTime());
            request.addProperty("otp", otp);
            residentAuthDTO.setRequestedAuth(requestedAuth);

            String requestBlock = new Gson().toJson(request);
            residentAuthDTO.setRequest(CryptoUtil.encryptAESGCMNOPadding(requestBlock));
            residentAuthDTO.setRequestHMAC(CryptoUtil.generateHMAC(requestBlock));
            residentAuthDTO.setThumbprint(Base64.encodeBase64URLSafeString(CryptoUtil.generateThumbprint(CryptoUtil.getCertificate(apiConfig))));

            System.out.println(apiConfig.getResidentEKYCURL());

            return RequestUtil.performRequest(apiConfig, apiConfig.getResidentEKYCURL(), authToken, new Gson().toJson(residentAuthDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        System.out.println(Constants.getResidentEKYCURL());
//        System.out.println("\n\n");
//        System.out.print("Enter the encrypted identity block > ");
//        Scanner scn = new Scanner(System.in);
//
//        String cipherText = scn.next();
//        String identityBlock = null;
//        try {
//            identityBlock = CryptoUtil.symmetricDecryptResponse(cipherText);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(identityBlock);
//        return null;
    }
}
