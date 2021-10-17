package com.mauriced.atmapplication.dao.impl;

import com.mauriced.atmapplication.dao.AtmDao;
import com.mauriced.atmapplication.model.Account;
import com.mauriced.atmapplication.model.BankNote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Maurice Danaher
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AtmDaoImplTest {

    @Autowired
    AtmDao dao;

    @Test
    public void testGetAccountValid() {
        Account account = dao.getAccount(123456789, 1234);

        assertEquals(123456789, account.getAccountNumber());
        assertEquals(800, account.getOpeningBalance());
        assertEquals(200, account.getOverdraft());
    }

    @Test
    public void testGetAccountInvalidAccountNumber() {
        assertNull(dao.getAccount(12345678, 1234));
    }

    @Test
    public void testGetAccountInvalidPin() {
        assertNull(dao.getAccount(123456789, 123));
    }

    @Test
    public void testCheckAccountExistsValid() {
        assertTrue(dao.checkAccountExists(123456789));
    }

    @Test
    public void testCheckAccountExistsInvalid() {
        assertFalse(dao.checkAccountExists(12345678));
    }

    @Test
    public void testUpdateAccountBalanceValid() {
        Account account = dao.getAccount(123456789, 1234);

        assertEquals(123456789, account.getAccountNumber());
        assertEquals(800, account.getOpeningBalance());
        assertEquals(200, account.getOverdraft());

        dao.updateAccountBalance(123456789, 200);

        account = dao.getAccount(123456789, 1234);

        assertEquals(123456789, account.getAccountNumber());
        assertEquals(600, account.getOpeningBalance());
        assertEquals(200, account.getOverdraft());
    }

    @Test
    public void testGetAtmBalance() {
        List<BankNote> bankNoteList = dao.getAtmBalance();
        assertNotNull(bankNoteList);
        assertEquals(4, bankNoteList.size());
    }

    @Test
    public void testWithdrawFromAtm() {
        BankNote bankNote = new BankNote(20, 5);
        List<BankNote> bankNoteList = new ArrayList<>();
        bankNoteList.add(bankNote);

        dao.withdrawFromAtm(bankNoteList);
    }

}