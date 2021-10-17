package com.mauriced.atmapplication.service;

import com.mauriced.atmapplication.exception.ServiceException;
import com.mauriced.atmapplication.model.Account;
import com.mauriced.atmapplication.model.Withdrawal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Maurice Danaher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AtmServiceTest {

    @Autowired
    AtmService service;

    @Test
    public void testGetAccountValid() {
        Account account = service.getAccount("123456789", "1234");

        assertEquals(123456789, account.getAccountNumber());
        assertEquals(800, account.getOpeningBalance());
        assertEquals(200, account.getOverdraft());
    }

    @Test(expected = ServiceException.class)
    public void testGetAccountInvalidAccountNumber() {
        service.getAccount("1234", "1234");
    }

    @Test(expected = ServiceException.class)
    public void testGetAccountInvalidPin() {
        service.getAccount("123456789", "123");
    }

    @Test(expected = ServiceException.class)
    public void testGetAccountInvalidAccountNumberFormat() {
        service.getAccount("abcdefgh", "1234");
    }

    @Test(expected = ServiceException.class)
    public void testGetAccountInvalidPinFormat() {
        service.getAccount("123456789", "abcd");
    }

    @Test
    public void testWithdrawValid() {
        Withdrawal withdrawal = service.withdraw("123456789", "1234", "100");

        assertEquals(123456789, withdrawal.getAccount().getAccountNumber());
        assertEquals(700, withdrawal.getAccount().getOpeningBalance());
        assertEquals(200, withdrawal.getAccount().getOverdraft());

        assertNotNull(withdrawal.getBankNoteList());
    }

}