package com.fintechblocks.paymentinitiationdemo.model;

import android.text.Editable;

import com.google.gson.annotations.SerializedName;

public class RemittanceInformation {
    // Fields
    @SerializedName("Unstructured")
    private String mUnstructured;

    @SerializedName("Reference")
    private String mReference;

    // Constructor
    public RemittanceInformation() {
    }

    // Getters and setters
    public String getUnstructured() {
        return mUnstructured;
    }

    public void setUnstructured(String unstructured) {
        this.mUnstructured = unstructured;
    }

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        this.mReference = reference;
    }
}
