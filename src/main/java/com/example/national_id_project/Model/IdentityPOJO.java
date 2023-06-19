package com.example.national_id_project.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityPOJO{
    public String phoneNumber;
    public String gender;
    public String dob;
    public String name;
    public Object emailId;
    @JsonProperty("Face")
    public Object face;
    public String age;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

    public Object getFace() {
        return face;
    }

    public void setFace(Object face) {
        this.face = face;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "identity{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", name='" + name + '\'' +
                ", emailId=" + emailId +
                ", face=" + face +
                ", age='" + age + '\'' +
                '}';
    }
}
