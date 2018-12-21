package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class PaymentSetupPOSTResponse {
    @SerializedName("Data")
    private PaymentSetupResponse mPaymentSetupResponse;

    @SerializedName("Risk")
    private Risk mRisk;

    @SerializedName("Links")
    private Links mLinks;

    @SerializedName("Meta")
    private Meta mMeta;

    // Constructor
    PaymentSetupPOSTResponse() {
    }

    // Getters and setters

    public PaymentSetupResponse getPaymentSetupResponse() {
        return mPaymentSetupResponse;
    }

    public void setPaymentSetupResponse(PaymentSetupResponse paymentSetupResponse) {
        this.mPaymentSetupResponse = paymentSetupResponse;
    }

    public Risk getRisk() {
        return mRisk;
    }

    public void setRisk(Risk risk) {
        this.mRisk = risk;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        this.mLinks = links;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        this.mMeta = meta;
    }
}
