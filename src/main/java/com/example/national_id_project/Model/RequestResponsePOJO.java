package com.example.national_id_project.Model;

import java.util.Date;

public class RequestResponsePOJO {

    public String id;
    public String version;
    public String transactionID;
    public Date responseTime;
    public Object errors;
    public Response response;


    public class Response{
        public String maskedMobile;
        public Object maskedEmail;
    }


}
