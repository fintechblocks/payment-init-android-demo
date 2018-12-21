package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class DebtorAgent {
    // Fields
    @SerializedName("SchemeName")
    private String mSchemeName;

    @SerializedName("Identification")
    private String mIdentification;

    // Constructor
    public DebtorAgent() {
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
