package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class PaymentSetupPOSTRequest {
    // Fields
    @SerializedName("Data")
    PaymentSetupInitiationData mPaymentSetupInitiationData;

    @SerializedName("Risk")
    Risk mRisk;

    // Constructor
    public PaymentSetupPOSTRequest() {
    }

    // Getters and setters
    public PaymentSetupInitiationData getPaymentSetupInitiationData() {
        return mPaymentSetupInitiationData;
    }

    public void setPaymentSetupInitiationData(PaymentSetupInitiationData paymentSetupInitiationData) {
        this.mPaymentSetupInitiationData = paymentSetupInitiationData;
    }

    public Risk getRisk() {
        return mRisk;
    }

    public void setRisk(Risk risk) {
        this.mRisk = risk;
    }
}
