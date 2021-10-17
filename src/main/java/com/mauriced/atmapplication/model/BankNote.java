package com.mauriced.atmapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BankNote model class
 *
 * @author Maurice Danaher
 */
public class BankNote {

    @JsonProperty("Bank Note")
    private int noteAmount;

    @JsonProperty("Count")
    private int noteCount;

    public BankNote(int noteAmount, int noteCount) {
        this.noteAmount = noteAmount;
        this.noteCount = noteCount;
    }

    public BankNote() {
        // Empty constructor
    }

    public int getNoteAmount() {
        return noteAmount;
    }

    public void setNoteAmount(int noteAmount) {
        this.noteAmount = noteAmount;
    }

    public int getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }
}
