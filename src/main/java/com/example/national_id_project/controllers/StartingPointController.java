package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.DTOs.OtpStarterParameter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

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





    @GetMapping("request")
    public String otpStarter(@RequestBody OtpStarterParameter allData) throws NoSuchAlgorithmException, KeyManagementException {

        /*Assigning The variables*/
        this.transactionID = allData.transactionId;
        this.individualID = allData.individualId;
        this.idType = allData.idType;
        this.channel = allData.channel;
        /**************************/
//        System.out.println(this.transactionID);
//        System.out.println(this.individualID);
//        System.out.println(this.idType);
//        System.out.println(this.channel);


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

        System.out.println("token has been generated");


        //OTP Request
        String otpRequestResponse = OtpRequest.requestOtpService(this.apiConfig, this.authToken, this.transactionID, this.individualID, this.idType, this.channel);
        //check if the response was successful
        System.out.println("The otp requsestResponse result");

        System.out.println(otpRequestResponse);
        System.out.println("*****************************************************************************************");

        return otpRequestResponse;


    }

    @GetMapping("auth")
    public String otpAuthRequest(@RequestBody ObjectNode objectNode) throws NoSuchAlgorithmException, KeyManagementException {


//        ApiConfig apiConfig = new ApiConfig();
//
//
//
//        /*for trusting all users*/
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//        //Client Auth Api
//        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);




        //Otp Auth Request
        otp = objectNode.get("otpSent").asText();
        System.out.println(otp);
        String residentAuthResponse = ResidentAuthRequest.otpAuthenticateResident(this.apiConfig, this.authToken, otp, this.transactionID, this.individualID, this.idType);
        System.out.println(residentAuthResponse);


        return residentAuthResponse;




    }


    @GetMapping("ekyc")
    public String EKYCRequestResponse() throws NoSuchAlgorithmException, KeyManagementException {

//        ApiConfig apiConfig = new ApiConfig();
//
//
//
//        /*for trusting all users*/
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//        //Client Auth Api
//        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);

        //EKYC Request IMPORTANT YOU CANNOT MAKE RESIDENT AUTH REQUEST AND EKYCREQUEST - MAKE SURE YOU REQUEST OTP BEFORE MAKING ONE OF THOSE REQUESTS
        String ekycRequestResponse = ResidentEKYCRequest.getEkycForResident(this.apiConfig, this.authToken, otp, this.transactionID, this.individualID, this.idType);
        System.out.println(ekycRequestResponse);

        return ekycRequestResponse;

    }


}
