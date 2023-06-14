package com.example.national_id_project.DTOs;

public class BaseDTO {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String id;
    private String version;

}
