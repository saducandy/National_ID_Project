package com.example.national_id_project.Model;

import java.util.Date;

public class ErrorCheckerAuth {

    public String transactionID;
    public String version;
    public String id;
    public Error errors;
    public Date responseTime;
    public Response response;
    public class Response{
        public boolean authStatus;
        public String authToken;
    }

    public class Error{
        public String errorCode;
        public String errorMessage;
        public String actionMessage;
    }

}