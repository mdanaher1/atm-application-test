package com.mauriced.atmapplication.model;

import java.util.List;

/**
 * ATM model class
 *
 * @author Maurice Danaher
 */
public class Atm {

    private List<BankNote> bankNotes;

    public List<BankNote> getBankNotes() {
        return bankNotes;
    }

    public void setBankNotes(List<BankNote> bankNotes) {
        this.bankNotes = bankNotes;
    }

    public int getAtmBalance() {
        int total = 0;

        for (BankNote bankNote : bankNotes) {
            total += bankNote.getNoteAmount() * bankNote.getNoteCount();
        }

        return total;
    }

}
