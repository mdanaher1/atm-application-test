package com.mauriced.atmapplication.controller;

import com.mauriced.atmapplication.model.Account;
import com.mauriced.atmapplication.service.AtmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Get Account Endpoint
 *
 * @author Maurice Danaher
 */
@RestController
public class GetAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAccountController.class);

    @Autowired
    AtmService atmService;

    /**
     * Controller endpoint method for getting account info
     *
     * @param accountNumber account number of account to retrieve
     * @param pin           pin of account to retrieve
     * @return Account      JSON response containing account info
     */
    @GetMapping(value = "/account/{ACCOUNT_NUMBER}/pin/{PIN}")
    public Account getAccount(@PathVariable("ACCOUNT_NUMBER") final String accountNumber,
                              @PathVariable("PIN") final String pin) {

        LOGGER.info("Entering GetAccountController with accountNumber: {}", accountNumber);

        return atmService.getAccount(accountNumber, pin);

    }

}
