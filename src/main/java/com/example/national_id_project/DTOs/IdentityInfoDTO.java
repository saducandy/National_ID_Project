package com.example.national_id_project.DTOs;

public class IdentityInfoDTO {

    /** Variable to hold language */
    private String language;

    /** Variable to hold value */
    private String value;
    public IdentityInfoDTO(String language,String value){
        this.language=language;
        this.value=value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}