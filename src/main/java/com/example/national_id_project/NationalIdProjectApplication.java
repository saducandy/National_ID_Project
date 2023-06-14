package com.example.national_id_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class NationalIdProjectApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        SpringApplication.run(NationalIdProjectApplication.class, args);

//        ApiConfig apiConfig = new ApiConfig();
////
////
////
//        /*for trusting all users*/
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, new TrustManager[] { new TrustAllManager() }, null);
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//        //Client Auth Api
//        String authToken = PartnerAuthRequest.authenticatePartner(apiConfig);
//
//        System.out.println("********************************************the token ends*********************************");
//
//        //OTP Request
//        String otpRequestResponse = OtpRequest.requestOtpService(apiConfig, authToken, "1231231234", "2413423819", ApiConfig.UIN, ApiConfig.PHONE);
//        //check if the response was successful
//        System.out.println(otpRequestResponse);
//        System.out.println("********************************************the OTP Request ends*********************************");

//
////        // ---- //
//        String otp = "111111";
//        //Otp Auth Request
//        String residentAuthResponse = ResidentAuthRequest.otpAuthenticateResident(apiConfig, authToken, otp, "1231231234", "2413423819", ApiConfig.UIN);
//        System.out.println(residentAuthResponse);
//        System.out.println("********************************************the OTP auth ends*********************************");
//
//
//        //EKYC Request IMPORTANT YOU CANNOT MAKE RESIDENT AUTH REQUEST AND EKYCREQUEST - MAKE SURE YOU REQUEST OTP BEFORE MAKING ONE OF THOSE REQUESTS
//        String ekycRequestResponse = ResidentEKYCRequest.getEkycForResident(apiConfig, authToken, otp, "1231231234", "2413423819", ApiConfig.UIN);
//        System.out.println(ekycRequestResponse);
//        System.out.println("********************************************the EKYC ends*********************************");



    }
}


