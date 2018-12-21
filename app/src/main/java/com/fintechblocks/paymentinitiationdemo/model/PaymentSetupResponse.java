package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PaymentSetupResponse {
    // Fileds
    @SerializedName("PaymentId")
    private String mPaymentId;

    @SerializedName("Status")
    private String mStatus;

    @SerializedName("CreationDateTime")
    private Date mCreationDateTime;

    @SerializedName("Initiation")
    private PaymentSetupInitiation mPaymentSetupInitiation;

    // Constructor
    public PaymentSetupResponse() {
    }

    // Getters and setters

    public String getPaymentId() {
        return mPaymentId;
    }

    public void setPaymentId(String paymentId) {
        this.mPaymentId = paymentId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public Date getCreationDateTime() {
        return mCreationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.mCreationDateTime = creationDateTime;
    }

    public PaymentSetupInitiation getPaymentSetupInitiation() {
        return mPaymentSetupInitiation;
    }

    public void setPaymentSetupInitiation(PaymentSetupInitiation paymentSetupInitiation) {
        this.mPaymentSetupInitiation = paymentSetupInitiation;
    }
}
