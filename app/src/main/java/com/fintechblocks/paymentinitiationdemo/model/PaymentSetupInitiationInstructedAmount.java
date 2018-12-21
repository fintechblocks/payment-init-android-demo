package com.fintechblocks.paymentinitiationdemo.model;

import android.text.Editable;

import com.google.gson.annotations.SerializedName;

public class PaymentSetupInitiationInstructedAmount {
    // Fields
    @SerializedName("Amount")
    private String mAmount;

    @SerializedName("Currency")
    private String mCurrency;

    // Constructor
    public PaymentSetupInitiationInstructedAmount() {
    }

    // Getters and setters
    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        this.mAmount = amount;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        this.mCurrency = currency;
    }
}
