package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class Meta {
    // Fields
    @SerializedName("TotalPages")
    private int mTotalPages;

    // Constructor
    public Meta() {
    }

    // Getters and setters

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        this.mTotalPages = totalPages;
    }
}
