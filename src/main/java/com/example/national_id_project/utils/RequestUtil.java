package com.example.national_id_project.utils;

import com.example.national_id_project.ApiConfig;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtil {
    public static String performRequest(ApiConfig apiConfig, String requestUrl, String authToken, String jsonString) throws Exception {

        System.out.println(jsonString);

        URL url = new URL (requestUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", authToken);
        String signature = CryptoUtil.sign(apiConfig, jsonString);
        con.setRequestProperty("Signature", signature);
        System.out.println(signature);

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
            return response.toString();


        }

    }
}
