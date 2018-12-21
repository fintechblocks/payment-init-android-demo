package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class PaymentSubmissionData {
    // Fields
    @SerializedName("Data")
    private PaymentSubmission mPaymentSubmission;

    @SerializedName("Meta")
    private Meta mMeta;

    @SerializedName("Links")
    private Links mLinks;

    // Constructor
    private PaymentSubmissionData() {
    }

    // Getters and setters
    public PaymentSubmission getPaymentSubmission() {
        return mPaymentSubmission;
    }

    public void setPaymentSubmission(PaymentSubmission paymentSubmission) {
        this.mPaymentSubmission = paymentSubmission;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        this.mMeta = meta;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        this.mLinks = links;
    }
}
