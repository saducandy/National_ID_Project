package com.example.national_id_project.DTOs;

import com.google.gson.JsonObject;

public class ResidentAuthDTO extends BaseDTO{
    /**
     * {
     *   "id": "fayda.identity.auth",
     *   "version": "v1",
     *   "requestTime": "2019-02-15T10:01:57.086+05:30",
     *   "env": "<Target environment>",
     *   "domainUri": "<URI of the authentication server>",
     *   "transactionID": "<Transaction ID of the authentication request>",
     *   "requestedAuth": {
     *     "otp": true,
     *     "demo": false,
     *     "bio": false
     *   },
     *   "consentObtained": true,
     *   "individualId": "9830872690593682",
     *   "individualIdType": "VID",
     *   "thumbprint": "<Thumbprint of the public key certficate used for enryption of sessionKey. This is necessary for key rotaion>",
     *   "requestSessionKey": "<Encrypted and Base64-URL-encoded session key>",
     *   "requestHMAC": "<SHA-256 of request block before encryption and then hash is encrypted using the requestSessionKey>",
     *   //Encrypted with session key and base-64-URL encoded
     *
     *   "request": {
     *     "timestamp": "2019-02-15T10:01:56.086+05:30 - ISO format timestamp",
     *     "otp": "111111"
     *   }
     * }
     */

    private String requestTime;
    private String env;
    private String domainUri;
    private String transactionID;
    private JsonObject requestedAuth;
    private boolean consentObtained;
    private String individualId;
    private String individualIdType;
    private String thumbprint;
    private String requestSessionKey;
    private String requestHMAC;
    private String request;

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDomainUri() {
        return domainUri;
    }

    public void setDomainUri(String domainUri) {
        this.domainUri = domainUri;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public JsonObject getRequestedAuth() {
        return requestedAuth;
    }

    public void setRequestedAuth(JsonObject requestedAuth) {
        this.requestedAuth = requestedAuth;
    }

    public boolean isConsentObtained() {
        return consentObtained;
    }

    public void setConsentObtained(boolean consentObtained) {
        this.consentObtained = consentObtained;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public String getIndividualIdType() {
        return individualIdType;
    }

    public void setIndividualIdType(String individualIdType) {
        this.individualIdType = individualIdType;
    }

    public String getThumbprint() {
        return thumbprint;
    }

    public void setThumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
    }

    public String getRequestSessionKey() {
        return requestSessionKey;
    }

    public void setRequestSessionKey(String requestSessionKey) {
        this.requestSessionKey = requestSessionKey;
    }

    public String getRequestHMAC() {
        return requestHMAC;
    }

    public void setRequestHMAC(String requestHMAC) {
        this.requestHMAC = requestHMAC;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
