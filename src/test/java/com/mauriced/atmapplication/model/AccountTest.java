package com.mauriced.atmapplication.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Maurice Danaher
 */
public class AccountTest {

    @Test
    public void testSettersAndGetters() {
        Account account = createAccount();

        assertEquals(123123123, account.getAccountNumber());
        assertEquals(123, account.getOverdraft());
        assertEquals(1234, account.getOpeningBalance());
    }

    @Test
    public void testToString() {
        Account account = createAccount();

        assertEquals("Account Number: 123123123, Opening Balance: 1234, Overdraft: 123", account.toString());
    }

    private Account createAccount() {
        Account account = new Account();

        account.setAccountNumber(123123123);
        account.setOverdraft(123);
        account.setOpeningBalance(1234);

        return account;
    }

}