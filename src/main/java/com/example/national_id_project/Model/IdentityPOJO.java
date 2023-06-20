package com.example.national_id_project.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class IdentityPOJO{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public String phoneNumber;
    public String gender;
    public String dob;
    public String name;

    @Transient
    public Object emailId;
    @JsonProperty("Face")
    @Transient
    public Object face;
    public String age;
    private String transactionId;
    private String individualId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

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
        return "IdentityPOJO{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", name='" + name + '\'' +
                ", emailId=" + emailId +
                ", face=" + face +
                ", age='" + age + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", individualId='" + individualId + '\'' +
                '}';
    }
}
