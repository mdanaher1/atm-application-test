package com.mauriced.atmapplication.model;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maurice Danaher
 */
public class WithdrawalTest {

    @Mock
    Account account;

    @Mock
    List<BankNote> bankNoteList;

    @Test
    public void testSettersAndGetters() {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAccount(account);
        withdrawal.setBankNoteList(bankNoteList);

        assertEquals(account, withdrawal.getAccount());
        assertEquals(bankNoteList, withdrawal.getBankNoteList());
    }
}