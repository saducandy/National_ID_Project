package com.example.national_id_project.controllers;


import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.DTOs.AuthDTO;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthRequest {

    AuthDTO authDTO;
    public static String auth;

    public AuthRequest(AuthDTO authDTO) {
        this.authDTO = authDTO;
    }


    public String performAuth(ApiConfig apiConfig) throws Exception {

        JSONObject jsonObject = new JSONObject()
                .put("id", authDTO.getId())
                .put("version", authDTO.getVersion())
                .put("requesttime", authDTO.getRequestTime())
                .put("request", new JSONObject()
                        .put("clientId", authDTO.getClientId())
                        .put("secretKey", authDTO.getSecretKey())
                        .put("appId", authDTO.getAppId())
                );
        String jsonString = jsonObject.toString();
        System.out.println("JSON REQUEST  > " + jsonString);
        sendRequest(jsonString, apiConfig);

        return jsonString;
    }

    private static String sendRequest(String jsonString, ApiConfig apiConfig) throws Exception{

        URL url = new URL (apiConfig.getClientAuthUrl());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            auth = con.getHeaderFields().get("Authorization").get(0);
            System.out.println(auth);
            return auth;
        }

    }



}
