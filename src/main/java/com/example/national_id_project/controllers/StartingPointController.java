package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.Model.AutoGeneratedRespObj;
import com.example.national_id_project.Model.ErrorCheckerAuth;
import com.example.national_id_project.Model.IdentityPOJO;
import com.example.national_id_project.Model.TransactionDetail;
import com.example.national_id_project.Repository.TransactionDetailRepo;
import com.example.national_id_project.utils.CryptoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/otp/")
public class StartingPointController {


    private String transactionID;
    private String individualID;
    private String idType;
    private String channel;
    private String otp;
    private ApiConfig apiConfig;
    private String authToken;
    private TransactionDetailRepo transactionDetailRepo;


    @Autowired
    public void setTransactionDetailRepo(TransactionDetailRepo transactionDetailRepo) {
        this.transactionDetailRepo = transactionDetailRepo;
    }

    @PostMapping("request")
    public String otpStarter(@RequestBody ObjectNode objectNode) throws NoSuchAlgorithmException, KeyManagementException {


        TransactionDetail transactionDetail1;
        transactionDetail1 = transactionDetailRepo.findTopByOrderByCreatedAtDesc();
        String trID = transactionDetail1.getTransactionId();



        long after = Long.parseLong(trID) + 1;
        String trID1 = String.format("%010d", after);


        /*Assigning The variables*/
//        this.transactionID = allData.transactionId;
//        this.individualID = allData.individualId;
//        this.idType = allData.idType;
//        this.channel = allData.channel;
        /**************************/
//        System.out.println(this.transactionID);
//        System.out.println(this.individualID);
//        System.out.println(this.idType);
//        System.out.println(this.channel);

        TransactionDetail transactionDetail = new TransactionDetail();
        String transactionId = trID1;
//        String transactionId = "0000000001";

        transactionDetail.setTransactionId(transactionId);
        System.out.println(transactionId);
        String individualId = objectNode.get("individualId").asText();
        System.out.println(individualId);
        transactionDetail.setIndividualId(individualId);

        String idType = objectNode.get("idType").asText();
        System.out.println(idType);
        String channel = objectNode.get("channel").asText();
        System.out.println(channel);


        if (idType.equals("UIN")){
            System.out.println("IN-UIN");
           transactionDetail.setUIN(true);
        }
        if (idType.equals("VID")){
            System.out.println("IN-VID");
            transactionDetail.setVID(true);
        }

        if (channel.equals("EMAIL")){
            System.out.println("IN-EMAIL");
            transactionDetail.setEMAIL(true);
        }
        if (channel.equals("PHONE")){
            System.out.println("IN-PHONE");
            transactionDetail.setPHONE(true);
        }

        transactionDetail.setCreatedAt(ZonedDateTime.now(ZoneId.of("Africa/Addis_Ababa")));








        ApiConfig apiConfig = new ApiConfig();


        this.apiConfig = apiConfig;


        /*for trusting all users*/
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        System.out.println("All users has been trusted");


        //Client Auth Api
        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);
        this.authToken = authToken;
        System.out.println(this.authToken);

        System.out.println("token has been generated");


        //OTP Request
        String otpRequestResponse = OtpRequest.requestOtpService(this.apiConfig, this.authToken, transactionId, individualId, idType, channel);
        //check if the response was successful
        System.out.println("The otp requsestResponse result");

        System.out.println(otpRequestResponse);
        transactionDetailRepo.save(transactionDetail);
        System.out.println("*****************************************************************************************");

        return otpRequestResponse;


    }

    @PostMapping("auth")
    public String otpAuthRequest(@RequestBody ObjectNode objectNode) throws NoSuchAlgorithmException, KeyManagementException, JsonProcessingException {



        transactionID = objectNode.get("transactionId").asText();


        TransactionDetail transactionDetail2 = transactionDetailRepo.findByTransactionId(transactionID);




        individualID = transactionDetail2.getIndividualId();

        if(transactionDetail2.isVID()){
            idType = ApiConfig.VID;
        }

        if(transactionDetail2.isUIN()){
            idType = ApiConfig.UIN;
        }

        if(transactionDetail2.isPHONE()){
            channel = ApiConfig.PHONE;
        }

        if(transactionDetail2.isEMAIL()){
            channel = ApiConfig.EMAIL;
        }



//        String channel = objectNode.get("channel").asText();
        System.out.println(transactionID);
        System.out.println(individualID);
        System.out.println(idType);
        System.out.println(channel);



        //Otp Auth Request
        String otp = objectNode.get("otpSent").asText();
        System.out.println(otp);
        String residentAuthResponse = ResidentAuthRequest.otpAuthenticateResident(this.apiConfig, this.authToken, otp, transactionID, individualID, idType);
        System.out.println(residentAuthResponse);


        ObjectMapper objectMapper = new ObjectMapper();
        ErrorCheckerAuth errorCheckerAuth = objectMapper.readValue(residentAuthResponse, ErrorCheckerAuth.class);

        if (errorCheckerAuth.errors == null) {
            transactionDetailRepo.updateStatusAuth(true, transactionID);
        }


        return residentAuthResponse;




    }


    @PostMapping("ekyc")
    public String EKYCRequestResponse(@RequestBody ObjectNode objectNode) throws Exception {

        transactionID = objectNode.get("transactionId").asText();
//        String individualId = objectNode.get("individualId").asText();
//        String idType = objectNode.get("idType").asText();
//        String channel = objectNode.get("channel").asText();


        TransactionDetail transactionDetail3 = transactionDetailRepo.findByTransactionId(transactionID);




        individualID = transactionDetail3.getIndividualId();

        if(transactionDetail3.isVID()){
            idType = ApiConfig.VID;
        }

        if(transactionDetail3.isUIN()){
            idType = ApiConfig.UIN;
        }

        if(transactionDetail3.isPHONE()){
            channel = ApiConfig.PHONE;
        }

        if(transactionDetail3.isEMAIL()){
            channel = ApiConfig.EMAIL;
        }



//        String channel = objectNode.get("channel").asText();
        System.out.println(transactionID);
        System.out.println(individualID);
        System.out.println(idType);
        System.out.println(channel);





        //Otp Auth Request
        String otp = objectNode.get("otpSent").asText();
        System.out.println(otp);


        //EKYC Request IMPORTANT YOU CANNOT MAKE RESIDENT AUTH REQUEST AND EKYCREQUEST - MAKE SURE YOU REQUEST OTP BEFORE MAKING ONE OF THOSE REQUESTS
        String ekycRequestResponse = ResidentEKYCRequest.getEkycForResident(this.apiConfig, this.authToken, otp, transactionID, individualID, idType);
        System.out.println(ekycRequestResponse);

//        CryptoUtil.symmetricDecryptResponse(this.apiConfig,"jVT4yqDKNfOyXsC2HN-kzTIIK3bKjlhnEp-lXJDBbwlQm4V6bIipQ_WftYG5sJWCojAvZGnci7r-lJY8zkxMrByTzQqM1RImUnoxYYiMQ6CiIPdy59UmwFyw9WMU7LJZq568FZuiGelzF-QXk1GNANM6ZJ-RzTA7k2lUs1Rfm_Sl9l1O5WAifTvtiGEno_010Bpu-Cc0a-m3Smp-tv-rO59i5YEQmRH99ye2RWuadd1hVYHfjctrvAGUwOq-HzUbaBeYNtaF75O3DAoR6nXDfevmhu1Ia3A6W_bKF5DHIwwycW0uEyQzVEYQkbcPCg-TjUr-p2El4pWcEggCJmFvxiNLRVlfU1BMSVRURVIjBV6LC96gMurAwrP3Nwokjunw69teVwA6PKfWP1blTixZYpMRs3oPu8zQ45D6VNxXf1xkz-yrC8RrBRUBC5L90NoLsu3MWyrHAXm3LnOUHHbLduASzhuC73AdXgEz4AQFGPDlGrQ8YsRpVZxV61ysBFFeoTiIRYP2-pB2j5fntYw4wBlrGZk6MeRuIRsoo5EdHVepaL1b94V7YLvL7BCHG0wfXT3g");


        ObjectMapper objectMapper = new ObjectMapper();
        AutoGeneratedRespObj autoGeneratedRespObj = objectMapper.readValue(ekycRequestResponse, AutoGeneratedRespObj.class);

        if (autoGeneratedRespObj.errors == null){

            String encryptedData = autoGeneratedRespObj.getResponse().getIdentity();
            String transactionId1 = autoGeneratedRespObj.transactionID;

            System.out.println(encryptedData);
            System.out.println(transactionId1);

            String decryptedData = CryptoUtil.symmetricDecryptResponse(this.apiConfig,encryptedData);

            ObjectMapper objectMapper1 = new ObjectMapper();
            IdentityPOJO identityPOJO = objectMapper1.readValue(decryptedData, IdentityPOJO.class);

            transactionDetailRepo.updateStatusKYC(true, transactionID);





            return decryptedData;

        }else {
            return ekycRequestResponse;
        }




    }


}
