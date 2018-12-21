package com.fintechblocks.paymentinitiationdemo.model;

import com.google.gson.annotations.SerializedName;

public class Risk {
    // Fields
    @SerializedName("PaymentContextCode")
    private String mPaymentContextCode;

    @SerializedName("MerchantCategoryCode")
    private String mMerchantCategoryCode;

    // Constructor
    public Risk() {
    }

    // Getters and setters
    public String getPaymentContextCode() {
        return mPaymentContextCode;
    }

    public void setPaymentContextCode(String paymentContextCode) {
        this.mPaymentContextCode = paymentContextCode;
    }

    public String getmerchantCategoryCode() {
        return mMerchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.mMerchantCategoryCode = merchantCategoryCode;
    }
}
