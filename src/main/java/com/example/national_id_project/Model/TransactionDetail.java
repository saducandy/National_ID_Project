package com.example.national_id_project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.ZonedDateTime;

@Entity
public class TransactionDetail {
    @Id
    private String transactionId;
    private String individualId;
    private boolean VID = false;
    private boolean UIN = false;
    private boolean KYC = false;
    private boolean Auth = false;

    private ZonedDateTime createdAt;
    private boolean EMAIL = false;
    private boolean PHONE = false;


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

    public boolean isVID() {
        return VID;
    }

    public void setVID(boolean VID) {
        this.VID = VID;
    }

    public boolean isUIN() {
        return UIN;
    }

    public void setUIN(boolean UIN) {
        this.UIN = UIN;
    }

    public boolean isKYC() {
        return KYC;
    }

    public void setKYC(boolean KYC) {
        this.KYC = KYC;
    }

    public boolean isAuth() {
        return Auth;
    }

    public void setAuth(boolean auth) {
        Auth = auth;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(boolean EMAIL) {
        this.EMAIL = EMAIL;
    }

    public boolean isPHONE() {
        return PHONE;
    }

    public void setPHONE(boolean PHONE) {
        this.PHONE = PHONE;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", individualId='" + individualId + '\'' +
                ", VID=" + VID +
                ", UIN=" + UIN +
                ", KYC=" + KYC +
                ", Auth=" + Auth +
                ", createdAt=" + createdAt +
                ", EMAIL=" + EMAIL +
                ", PHONE=" + PHONE +
                '}';
    }
}
