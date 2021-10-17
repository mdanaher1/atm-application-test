package com.mauriced.atmapplication.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maurice Danaher
 */
public class AtmTest {

    @Test
    public void testSettersAndGetters() {
        List<BankNote> bankNotes = new ArrayList<>();

        Atm atm = new Atm();
        atm.setBankNotes(bankNotes);

        assertEquals(bankNotes, atm.getBankNotes());
    }

    @Test
    public void testGetAtmBalance() {
        List<BankNote> bankNotes = new ArrayList<>();
        Atm atm = new Atm();
        atm.setBankNotes(bankNotes);

        assertEquals(0, atm.getAtmBalance());

        bankNotes.add(new BankNote(20,1));
        assertEquals(20, atm.getAtmBalance());

        bankNotes.add(new BankNote(50, 2));
        assertEquals(120, atm.getAtmBalance());
    }

}