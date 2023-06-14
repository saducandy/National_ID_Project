package com.example.national_id_project.DTOs;

public class OtpRequestDTO extends BaseDTO{

    private String requestTime;
    private String env = "Developer";
    private String domainUri;
    private String transactionID;
    private String individualId;
    private String individualType;
    private String[] otpChannel;


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

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public String getIndividualType() {
        return individualType;
    }

    public void setIndividualType(String individualType) {
        this.individualType = individualType;
    }

    public String[] getOtpChannel() {
        return otpChannel;
    }

    public void setOtpChannel(String[] otpChannel) {
        this.otpChannel = otpChannel;
    }
}
