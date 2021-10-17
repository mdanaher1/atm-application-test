package com.mauriced.atmapplication.controller;

import com.mauriced.atmapplication.model.Withdrawal;
import com.mauriced.atmapplication.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Withdraw Endpoint
 *
 * @author Maurice Danaher
 */
@RestController
public class WithdrawController {

    @Autowired
    AtmService atmService;

    /**
     * Endpoint to updateAccountBalance funds from an account
     *
     * @param accountNumber the account number to withdraw from
     * @param pin           the pin of the account
     * @param amount        the amount to withdraw
     * @return              json response
     */
    @GetMapping(value = "/account/{ACCOUNT_NUMBER}/pin/{PIN}/withdraw/{AMOUNT}")
    public Withdrawal withdraw(@PathVariable("ACCOUNT_NUMBER") final String accountNumber,
                               @PathVariable("PIN") final String pin,
                               @PathVariable("AMOUNT") final String amount) {


        return atmService.withdraw(accountNumber, pin, amount);
    }

}
