package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PaymentSubmission {
    // Fields
    @SerializedName("PaymentSubmissionId")
    private String mPaymentSubmissionId;

    @SerializedName("PaymentId")
    private String mPaymentId;

    @SerializedName("CreationDateTime")
    private Date mCreationDateTime;

    @SerializedName("Status")
    private String mStatus;

    // Constructor
    public PaymentSubmission() {
    }

    // Getters and setters
    public String getPaymentSubmissionId() {
        return mPaymentSubmissionId;
    }

    public void setPaymentSubmissionId(String paymentSubmissionId) {
        this.mPaymentSubmissionId = mPaymentSubmissionId;
    }

    public String getPaymentId() {
        return mPaymentId;
    }

    public void setPaymentId(String paymentId) {
        this.mPaymentId = paymentId;
    }

    public Date getCreationDateTime() {
        return mCreationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.mCreationDateTime = creationDateTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }
}
