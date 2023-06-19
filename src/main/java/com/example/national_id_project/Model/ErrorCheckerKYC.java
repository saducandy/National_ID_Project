package com.example.national_id_project.Model;

import java.util.ArrayList;
import java.util.Date;

public class ErrorCheckerKYC {

    public String transactionID;
    public String version;
    public String id;
    public ArrayList<Error> errors;
    public Date responseTime;
    public Response response;

    public class Error{
        public String errorCode;
        public String errorMessage;
        public String actionMessage;
    }

    public class Response{
        public boolean kycStatus;
        public String authToken;
        public Object thumbprint;
        public Object identity;
    }

}
