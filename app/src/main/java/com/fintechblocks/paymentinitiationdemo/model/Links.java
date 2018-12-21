package com.fintechblocks.paymentinitiationdemo.model;

public class Links {
    // Fields
    private String mSelf;

    private String mFirst;

    private String mPrev;

    private String mLast;

    // Constructor
    public Links() {
    }

    // Getters and setters
    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        this.mSelf = self;
    }

    public String getFirst() {
        return mFirst;
    }

    public void setFirst(String first) {
        this.mFirst = first;
    }

    public String getPrev() {
        return mPrev;
    }

    public void setPrev(String prev) {
        this.mPrev = prev;
    }

    public String getLast() {
        return mLast;
    }

    public void setLast(String last) {
        this.mLast = last;
    }
}
