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
public class GetAccountControllerTest {

    @Autowired
    GetAccountController getAccountController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccountValid() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("['Account No']", is(123456789)))
                .andExpect(jsonPath("Balance", is(800)))
                .andExpect(jsonPath("Overdraft", is(200)));

    }

    @Test
    public void testGetAccountInvalidAccountNumber() throws Exception {

        this.mockMvc.perform(get("/account/12345678/pin/1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_ACCOUNT_NUMBER.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_ACCOUNT_NUMBER.getErrorMessage())));

    }

    @Test
    public void testGetAccountInvalidPin() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_ACCOUNT_PIN.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_ACCOUNT_PIN.getErrorMessage())));

    }

    @Test
    public void testGetAccountInvalidAccountNumberFormat() throws Exception {

        this.mockMvc.perform(get("/account/abcdefgh/pin/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_ACCOUNT_NUMBER_FORMAT.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_ACCOUNT_NUMBER_FORMAT.getErrorMessage())));

    }

    @Test
    public void testGetAccountInvalidPinFormat() throws Exception {

        this.mockMvc.perform(get("/account/123456789/pin/abcd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode", is(Error.INVALID_PIN_FORMAT.getErrorCode())))
                .andExpect(jsonPath("errorMessage", is(Error.INVALID_PIN_FORMAT.getErrorMessage())));

    }

}
