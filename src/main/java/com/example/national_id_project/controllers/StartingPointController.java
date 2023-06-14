package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("start")
    public void otpStarter() throws NoSuchAlgorithmException, KeyManagementException {

        ApiConfig apiConfig = new ApiConfig();



        /*for trusting all users*/
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        //Client Auth Api
        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);

        //OTP Request
        String otpRequestResponse = OtpRequest.requestOtpService(apiConfig, authToken, "1231231234", "2413423819", ApiConfig.UIN, ApiConfig.PHONE);
        //check if the response was successful
        System.out.println(otpRequestResponse);
    }

    @GetMapping("start1")
    public void otpAuthRequest() throws NoSuchAlgorithmException, KeyManagementException {


        ApiConfig apiConfig = new ApiConfig();



        /*for trusting all users*/
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        //Client Auth Api
        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);



        String otp = "111111";
        //Otp Auth Request
        String residentAuthResponse = ResidentAuthRequest.otpAuthenticateResident(apiConfig, authToken, otp, "1231231234", "2413423819", ApiConfig.UIN);
        System.out.println(residentAuthResponse);

        //EKYC Request IMPORTANT YOU CANNOT MAKE RESIDENT AUTH REQUEST AND EKYCREQUEST - MAKE SURE YOU REQUEST OTP BEFORE MAKING ONE OF THOSE REQUESTS
        String ekycRequestResponse = ResidentEKYCRequest.getEkycForResident(apiConfig, authToken, otp, "1231231234", "2413423819", ApiConfig.UIN);
        System.out.println(ekycRequestResponse);

    }


}
