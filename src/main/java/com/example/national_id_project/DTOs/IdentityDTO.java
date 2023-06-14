package com.example.national_id_project.DTOs;

import java.util.List;

public class IdentityDTO {

    private String age;

    private String dob;

    List<IdentityInfoDTO> name;

    List<IdentityInfoDTO> gender;

    String phoneNumber;

    String emailId;

    List<IdentityInfoDTO> addressLine1;

    List<IdentityInfoDTO> addressLine2;

    List<IdentityInfoDTO> addressLine3;

    List<IdentityInfoDTO> location1;

    List<IdentityInfoDTO> location2;

    List<IdentityInfoDTO> location3;

    String postalCode;

    List<IdentityInfoDTO> fullAddress;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<IdentityInfoDTO> getName() {
        return name;
    }

    public void setName(List<IdentityInfoDTO> name) {
        this.name = name;
    }

    public List<IdentityInfoDTO> getGender() {
        return gender;
    }

    public void setGender(List<IdentityInfoDTO> gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<IdentityInfoDTO> getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(List<IdentityInfoDTO> addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public List<IdentityInfoDTO> getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(List<IdentityInfoDTO> addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public List<IdentityInfoDTO> getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(List<IdentityInfoDTO> addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public List<IdentityInfoDTO> getLocation1() {
        return location1;
    }

    public void setLocation1(List<IdentityInfoDTO> location1) {
        this.location1 = location1;
    }

    public List<IdentityInfoDTO> getLocation2() {
        return location2;
    }

    public void setLocation2(List<IdentityInfoDTO> location2) {
        this.location2 = location2;
    }

    public List<IdentityInfoDTO> getLocation3() {
        return location3;
    }

    public void setLocation3(List<IdentityInfoDTO> location3) {
        this.location3 = location3;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<IdentityInfoDTO> getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(List<IdentityInfoDTO> fullAddress) {
        this.fullAddress = fullAddress;
    }
}