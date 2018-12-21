package com.fintechblocks.paymentinitiationdemo.model;

import android.text.Editable;

import com.google.gson.annotations.SerializedName;

public class CreditorAgent {
    // Fields
    @SerializedName("SchemeName")
    private String mSchemeName;

    @SerializedName("Identification")
    private String mIdentification;

    // Constructor
    public CreditorAgent() {
    }

    // Getters and setters
    public String getSchemeName() {
        return mSchemeName;
    }

    public void setSchemeName(String schemeName) {
        this.mSchemeName = schemeName;
    }

    public String getIdentification() {
        return mIdentification;
    }

    public void setIdentification(String identification) {
        this.mIdentification = identification;
    }
}
