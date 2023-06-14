package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.DTOs.OtpStarterParameter;
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

    private String otp = "111111";
    private String transactionID;
    private String individualID;
    private String idType;
    private String channel;


    @GetMapping("request")
    public void otpStarter(@RequestBody OtpStarterParameter allData) throws NoSuchAlgorithmException, KeyManagementException {

        /*Assigning The variables*/
        this.transactionID = allData.transactionId;
        this.individualID = allData.individualId;
        this.idType = allData.idType;
        this.channel = allData.channel;
        /**************************/
        System.out.println(this.transactionID);
        System.out.println(this.individualID);
        System.out.println(this.idType);
        System.out.println(this.channel);


        ApiConfig apiConfig = new ApiConfig();

        System.out.println("Apiconfi object decleared");



        /*for trusting all users*/
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        System.out.println("All users has been trusted");


        //Client Auth Api
        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);

        System.out.println("token has been generated");


        //OTP Request
        String otpRequestResponse = OtpRequest.requestOtpService(apiConfig, authToken, this.transactionID, this.individualID, this.idType, this.channel);
        //check if the response was successful
        System.out.println("The otp requsestResponse result");

        System.out.println(otpRequestResponse);
        System.out.println("*****************************************************************************************");

    }

    @GetMapping("auth")
    public void otpAuthRequest() throws NoSuchAlgorithmException, KeyManagementException {


        ApiConfig apiConfig = new ApiConfig();



        /*for trusting all users*/
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        //Client Auth Api
        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);




        //Otp Auth Request
        String residentAuthResponse = ResidentAuthRequest.otpAuthenticateResident(apiConfig, authToken, otp, "1231231234", "2413423819", ApiConfig.UIN);
        System.out.println(residentAuthResponse);



    }


    @GetMapping("ekyc")
    public void EKYCRequestResponse() throws NoSuchAlgorithmException, KeyManagementException {

        ApiConfig apiConfig = new ApiConfig();



        /*for trusting all users*/
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        //Client Auth Api
        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);

        //EKYC Request IMPORTANT YOU CANNOT MAKE RESIDENT AUTH REQUEST AND EKYCREQUEST - MAKE SURE YOU REQUEST OTP BEFORE MAKING ONE OF THOSE REQUESTS
        String ekycRequestResponse = ResidentEKYCRequest.getEkycForResident(apiConfig, authToken, otp, "1231231234", "2413423819", ApiConfig.UIN);
        System.out.println(ekycRequestResponse);

    }


}
