package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class AuthCallbackPayload {
    // Fields
    @SerializedName("state")
    private String mState;

   @SerializedName("code")
    private String mCode;

   public AuthCallbackPayload(){
   }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }
}
