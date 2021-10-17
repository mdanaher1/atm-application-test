package com.mauriced.atmapplication.controller;

import com.mauriced.atmapplication.constants.Error;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Maurice Danaher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class WithdrawControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWithdrawValid() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/1234/withdraw/175"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("['Account Details'].['Account No']", is(123456789)))
                .andExpect(jsonPath("['Account Details'].Balance", is(625)))
                .andExpect(jsonPath("['Account Details'].Overdraft", is(200)))
                .andExpect(jsonPath("['Notes Withdrawn'][0].['Bank Note']", is(50)))
                .andExpect(jsonPath("['Notes Withdrawn'][0].Count", is(3)))
                .andExpect(jsonPath("['Notes Withdrawn'][1].['Bank Note']", is(20)))
                .andExpect(jsonPath("['Notes Withdrawn'][1].Count", is(1)))
                .andExpect(jsonPath("['Notes Withdrawn'][2].['Bank Note']", is(5)))
                .andExpect(jsonPath("['Notes Withdrawn'][2].Count", is(1)));

    }

    @Test
    public void testWithdrawInvalidAccountNumber() throws Exception {

        this.mockMvc.perform(get("/account/12345678/pin/1234/withdraw/175"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_ACCOUNT_NUMBER.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_ACCOUNT_NUMBER.getErrorMessage())));

    }

    @Test
    public void testWithdrawInvalidPin() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/123/withdraw/175"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_ACCOUNT_PIN.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_ACCOUNT_PIN.getErrorMessage())));

    }

    @Test
    public void testWithdrawInvalidAccountNumberFormat() throws Exception {

        this.mockMvc.perform(get("/account/abcdefgh/pin/1234/withdraw/175"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_ACCOUNT_NUMBER_FORMAT.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_ACCOUNT_NUMBER_FORMAT.getErrorMessage())));

    }

    @Test
    public void testWithdrawInvalidPinFormat() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/abcd/withdraw/175"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_PIN_FORMAT.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_PIN_FORMAT.getErrorMessage())));

    }

    @Test
    public void testWithdrawInvalidAmountFormat() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/1234/withdraw/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_AMOUNT_FORMAT.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_AMOUNT_FORMAT.getErrorMessage())));

    }

    @Test
    public void testWithdrawAmountGreaterThanWithdrawableBalance() throws Exception {
        this.mockMvc.perform(get("/account/123456789/pin/1234/withdraw/9000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.UNAVAILABLE_ACCOUNT_FUNDS.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.UNAVAILABLE_ACCOUNT_FUNDS.getErrorMessage())));
    }

    @Test
    public void testWithdrawAmountInvalidMultiple() throws Exception {
        this.mockMvc.perform(get("/account/123456789/pin/1234/withdraw/111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_AMOUNT_MULTIPLE.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_AMOUNT_MULTIPLE.getErrorMessage())));
    }

}
