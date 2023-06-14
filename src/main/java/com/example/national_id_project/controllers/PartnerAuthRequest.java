package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.DTOs.AuthDTO;
import com.example.national_id_project.utils.CryptoUtil;


public class PartnerAuthRequest {

    public static String authenticatePartner(ApiConfig apiConfig){

        AuthDTO authDTO = new AuthDTO();
        authDTO.setId("1234512345");
        authDTO.setRequestTime(CryptoUtil.getTime());
        authDTO.setAppId(apiConfig.getAppId());
        authDTO.setClientId(apiConfig.getClientId());
        authDTO.setSecretKey(apiConfig.getSecretKey());

        AuthRequest authRequest = new AuthRequest(authDTO);
        String auth = null;

        try {
            authRequest.performAuth(apiConfig);
            System.out.println("Authorization > " + authRequest.auth);
            return authRequest.auth;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
