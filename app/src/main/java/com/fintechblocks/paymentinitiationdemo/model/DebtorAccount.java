package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class DebtorAccount {
    // Fields
    @SerializedName("SchemeName")
    private String mSchemeName;

    @SerializedName("Identification")
    private String mIdentification;

    @SerializedName("Name")
    private String mName;

    @SerializedName("SecondaryIdentification")
    private String mSecondaryIdentification;

    // Constructor
    public DebtorAccount(){
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

    public String getAccountName() {
        return mName;
    }

    public void setAccountName(String name) {
        this.mName = name;
    }

    public String getSecondaryIdentification() {
        return mSecondaryIdentification;
    }

    public void setSecondaryIdentification(String secondaryIdentification) {
        this.mSecondaryIdentification = secondaryIdentification;
    }
}
