package com.example.national_id_project.DTOs;


import com.example.national_id_project.DTOs.helpers.Demographics;


public class ResidentDemoAuthDTO extends ResidentAuthDTO{

    private Demographics demographics;


    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }
}
