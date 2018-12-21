package com.fintechblocks.paymentinitiationdemo.model;

import android.text.Editable;

import com.google.gson.annotations.SerializedName;

public class PaymentSetupInitiation {
    // Fields
    @SerializedName("InstructionIdentification")
    private String mInstructionIdentification;

    @SerializedName("EndToEndIdentification")
    private String mEndToEndIdentification;

    @SerializedName("InstructedAmount")
    private PaymentSetupInitiationInstructedAmount mInstructedAmount;

    @SerializedName("DebtorAgent")
    private DebtorAgent mDebtorAgent;

    @SerializedName("DebtorAccount")
    private DebtorAccount mDebtorAccount;

    @SerializedName("CreditorAgent")
    private CreditorAgent mCreditorAgent;

    @SerializedName("CreditorAccount")
    private CreditorAccount mCreditorAccount;

    @SerializedName("RemittanceInformation")
    private RemittanceInformation mRemittanceInformation;

    // Constructor
    public PaymentSetupInitiation() {
    }

    // Getters and setters

    public String getInstructionIdentification() {
        return mInstructionIdentification;
    }

    public void setInstructionIdentification(String instructionIdentification) {
        this.mInstructionIdentification = instructionIdentification;
    }

    public String getEndToEndIdentification() {
        return mEndToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.mEndToEndIdentification = endToEndIdentification;
    }

    public PaymentSetupInitiationInstructedAmount getInstructedAmount() {
        return mInstructedAmount;
    }

    public void setInstructedAmount(PaymentSetupInitiationInstructedAmount instructedAmount) {
        this.mInstructedAmount = instructedAmount;
    }

    public DebtorAgent getDebtorAgent() {
        return mDebtorAgent;
    }

    public void setDebtorAgent(DebtorAgent debtorAgent) {
        this.mDebtorAgent = debtorAgent;
    }

    public DebtorAccount getDebtorAccount() {
        return mDebtorAccount;
    }

    public void setDebtorAccount(DebtorAccount debtorAccount) {
        this.mDebtorAccount = debtorAccount;
    }

    public CreditorAgent getCreditorAgent() {
        return mCreditorAgent;
    }

    public void setCreditorAgent(CreditorAgent creditorAgent) {
        this.mCreditorAgent = creditorAgent;
    }

    public CreditorAccount getCreditorAccount() {
        return mCreditorAccount;
    }

    public void setCreditorAccount(CreditorAccount creditorAccount) {
        this.mCreditorAccount = creditorAccount;
    }

    public RemittanceInformation getRemittanceInformation() {
        return mRemittanceInformation;
    }

    public void setRemittanceInformation(RemittanceInformation remittanceInformation) {
        this.mRemittanceInformation = remittanceInformation;
    }
}
