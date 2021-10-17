package com.mauriced.atmapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Account model class
 *
 * @author Maurice Danaher
 */

@JsonRootName("Account Details")
public class Account {

    @JsonProperty("Account No")
    private int accountNumber;

    @JsonProperty("Balance")
    private int openingBalance;

    @JsonProperty("Overdraft")
    private int overdraft;

    public Account() {
        // empty constructor
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }

    public int totalWithdraw() {
        return openingBalance + overdraft;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Account Number: ").append(this.accountNumber)
                .append(", Opening Balance: " ).append(this.openingBalance)
                .append(", Overdraft: " ).append(this.overdraft).toString();
    }
}
