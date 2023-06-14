package com.example.national_id_project.controllers;

import com.example.national_id_project.ApiConfig;
import com.example.national_id_project.DTOs.OtpRequestDTO;
import com.example.national_id_project.utils.CryptoUtil;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OtpRequest {




    public static String requestOtpService(ApiConfig apiConfig, String authToken, String transactionID, String individualID, String idType, String channel){

        /**APIconfig**/





        /**************************************************************************************************/
        try {
            OtpRequestDTO otpRequestDTO = createOtpRequestDTO(apiConfig, transactionID, individualID, idType, channel);
            String jsonString = new Gson().toJson(otpRequestDTO);
            System.out.println(jsonString);

            HttpURLConnection con = createHttpConnection(apiConfig, authToken, jsonString);
            sendJsonData(con, jsonString);

            return readResponse(con);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static OtpRequestDTO createOtpRequestDTO(ApiConfig apiConfig, String transactionID, String individualID, String idType, String channel) {
        OtpRequestDTO otpRequestDTO = new OtpRequestDTO();
        otpRequestDTO.setId("fayda.identity.otp");
        otpRequestDTO.setVersion(apiConfig.getVersion());
        otpRequestDTO.setRequestTime(CryptoUtil.getTime());
        otpRequestDTO.setEnv(apiConfig.getEnv());
        otpRequestDTO.setDomainUri(apiConfig.getBaseURL());
        otpRequestDTO.setTransactionID(transactionID);
        otpRequestDTO.setIndividualId(individualID);
        otpRequestDTO.setIndividualType(idType);
        otpRequestDTO.setOtpChannel(new String[]{channel});
        return otpRequestDTO;
    }

    private static HttpURLConnection createHttpConnection(ApiConfig apiConfig, String authToken, String jsonString) throws IOException {
        URL urlObj = new URL(apiConfig.getOtpReqURL());
        HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", authToken);
        String signature = null;
        try {
            signature = CryptoUtil.sign(apiConfig, jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("Signature", signature);
        System.out.println(signature);
        con.setDoOutput(true);
        return con;
    }

    private static void sendJsonData(HttpURLConnection con, String jsonString) throws IOException {
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    private static String readResponse(HttpURLConnection con) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        return response.toString();
    }


}
