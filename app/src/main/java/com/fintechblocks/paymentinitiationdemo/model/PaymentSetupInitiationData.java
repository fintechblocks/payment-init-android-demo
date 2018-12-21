package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class PaymentSetupInitiationData {
    // Fields
    @SerializedName("Initiation")
    private PaymentSetupInitiation mPaymentSetupInitiation;

    // Constructor
    public PaymentSetupInitiationData() {
    }

    // Getters and setters
    public PaymentSetupInitiation getPaymentSetupInitiation() {
        return mPaymentSetupInitiation;
    }

    public void setPaymentSetupInitiation(PaymentSetupInitiation paymentSetupInitiation) {
        this.mPaymentSetupInitiation = paymentSetupInitiation;
    }
}
