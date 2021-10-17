package com.mauriced.atmapplication.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Maurice Danaher
 */
public class BankNoteTest {

    @Test
    public void testConstructor() {
        BankNote bankNote = new BankNote(20, 10);

        assertEquals(20, bankNote.getNoteAmount());
        assertEquals(10, bankNote.getNoteCount());
    }

    @Test
    public void testSettersAndGetters() {
        BankNote bankNote = new BankNote();

        bankNote.setNoteAmount(50);
        bankNote.setNoteCount(5);

        assertEquals(50, bankNote.getNoteAmount());
        assertEquals(5, bankNote.getNoteCount());
    }

}