package com.mauriced.atmapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Withdrawal model class to hold account details and notes withdrawn for withdrawl JSON response
 *
 * @author Maurice Danaher
 */
public class Withdrawal {

    @JsonProperty("Account Details")
    private Account account;

    @JsonProperty("Notes Withdrawn")
    private List<BankNote> bankNoteList;

    public Withdrawal() {
        // Empty Constructor
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<BankNote> getBankNoteList() {
        return bankNoteList;
    }

    public void setBankNoteList(List<BankNote> bankNoteList) {
        this.bankNoteList = bankNoteList;
    }
}
