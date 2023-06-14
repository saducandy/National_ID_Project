package com.example.national_id_project.controllers;

import com.example.national_id_project.DTOs.ResidentDemoAuthDTO;
import com.example.national_id_project.utils.CryptoUtil;
import com.google.gson.Gson;



public class ResidentDemoAuthRequest {

    private String auth;
    private boolean identity = false;
    private ResidentDemoAuthDTO residentDemoAuthDTO;


    public ResidentDemoAuthRequest(ResidentDemoAuthDTO residentDemoAuthDTO, String auth) {
        this.auth = auth;
        this.residentDemoAuthDTO = residentDemoAuthDTO;
    }

    public void performResidentAuthRequest() throws Exception {

        String jsonString = new Gson().toJson(residentDemoAuthDTO);
        System.out.println(jsonString);
        System.out.println(CryptoUtil.sign(null, jsonString));

    }
}
