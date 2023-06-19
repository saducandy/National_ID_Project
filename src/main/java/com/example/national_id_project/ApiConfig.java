package com.example.national_id_project;

public class ApiConfig {

    private String transactionId;
    private String appId;
    private String clientId;
    private String secretKey;
    private String baseURL;
    private String partnerId;
    private String env;
    private String partnerApiKey;
    private String fispLicenseKey;
    private String faydaCertPath;
    private String p12Path;
    private String p12password;
    private String version;
    private  String authURL;
    private String otpReqURL;
    private String residentAuthURL;
    private String residentEKYCURL;
    private final String splitter = "#KEY_SPLITTER#";
    public static final String VID = "VID";
    public static final String UIN = "UIN";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";


    public ApiConfig() {
        this.appId = System.getenv("APP_ID");
        this.baseURL = System.getenv("BASE_URL");
        this.secretKey = System.getenv("SECRET_KEY");
        this.clientId = System.getenv("CLIENT_ID");
        this.partnerApiKey = System.getenv("API_KEY");
        this.fispLicenseKey = System.getenv("FISP_KEY");
        this.faydaCertPath = System.getenv("FAYDA_CERT");
        this.p12Path = System.getenv("KEYPAIR_PATH");
        this.p12password = System.getenv("P12_PASSWORD");
        this.version = System.getenv("VERSION");
        this.env = System.getenv("FAYDA_ENV");
        this.partnerId = System.getenv("PARTNER_ID");

        this.authURL = "{base_url}/v1/authmanager/authenticate/clientidsecretkey";
        this.otpReqURL = "{base_url}/idauthentication/v1/otp/{fisp}/{partnerId}/{partnerApiKey}";
        this.residentAuthURL = "{base_url}/idauthentication/v1/auth/{fisp}/{partnerId}/{partnerApiKey}";
        this.residentEKYCURL = "{base_url}/idauthentication/v1/kyc/{fisp}/{partnerId}/{partnerApiKey}";
    }


    public String getSplitter(){
        return splitter;
    }

    public String getClientAuthUrl(){
        return authURL.replace("{base_url}", baseURL);
    }

    public String getOtpReqURL(){
        return otpReqURL.replace("{base_url}", baseURL)
                .replace("{fisp}", fispLicenseKey)
                .replace("{partnerId}", partnerId)
                .replace("{partnerApiKey}", partnerApiKey);
    }

    public String getResidentAuthURL(){
        return residentAuthURL.replace("{base_url}", baseURL)
                .replace("{fisp}", fispLicenseKey)
                .replace("{partnerId}", partnerId)
                .replace("{partnerApiKey}", partnerApiKey);
    }

    public String getResidentEKYCURL(){
        return residentEKYCURL.replace("{base_url}", baseURL)
                .replace("{fisp}", fispLicenseKey)
                .replace("{partnerId}", partnerId)
                .replace("{partnerApiKey}", partnerApiKey);
    }

    public String getAppId() {
        return appId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getEnv() {
        return env;
    }

    public String getPartnerApiKey() {
        return partnerApiKey;
    }

    public String getFispLicenseKey() {
        return fispLicenseKey;
    }

    public String getFaydaCertPath() {
        return faydaCertPath;
    }

    public String getP12Path() {
        return p12Path;
    }

    public String getP12password() {
        return p12password;
    }

    public String getVersion() {
        return version;
    }


}
