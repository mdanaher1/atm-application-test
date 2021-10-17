package com.mauriced.atmapplication.service;


import com.mauriced.atmapplication.constants.Error;
import com.mauriced.atmapplication.dao.AtmDao;
import com.mauriced.atmapplication.exception.ServiceException;
import com.mauriced.atmapplication.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for ATM
 *
 * @author Maurice Danaher
 */
@Service
public class AtmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtmService.class);

    private static final Atm ATM = new Atm();

    private static final Withdrawal WITHDRAWAL = new Withdrawal();

    private int accNum;
    private int accPin;
    private int withdrawAmount;

    @Autowired
    AtmDao dao;

    /**
     * Service method to validate account params,
     * check the account exits, then retrieve the account from the DB
     *
     * @param accountNumber account number of account to retrieve
     * @param pin           pin for account to retrieve
     * @return Account      JSON response containing account info
     */
    public Account getAccount(String accountNumber, String pin) {

        LOGGER.info("Entering getAccount in AtmService with accountNumber: {}", accountNumber);

        LOGGER.info("Validating account parameters");
        validateAccountParams(accountNumber, pin);

        if(!dao.checkAccountExists(accNum)) {
            throw new ServiceException(new ServiceError(Error.INVALID_ACCOUNT_NUMBER));
        }

        Account account = dao.getAccount(accNum, accPin);
        if(account == null) {
            throw new ServiceException(new ServiceError(Error.INVALID_ACCOUNT_PIN));
        }

        LOGGER.info("Leaving getAccount in AtmService");
        return account;
    }

    /**
     * Service method to withdraw notes from ATM and update account balance
     *
     * @param accountNumber account number of account to withdraw from
     * @param pin           pin number of account to withdraw from
     * @param amount        amount to be withdrawn
     * @return Withdrawl    withdrawal object that holds account details and list of notes withdrawn
     */
    public Withdrawal withdraw(String accountNumber, String pin, String amount) {

        LOGGER.info("Entering updateAccountBalance in AtmService with accountNumber: {}, amount: {}", accountNumber, amount);

        LOGGER.info("Validating updateAccountBalance parameters");
        validateWithdrawParams(accountNumber, pin, amount);

        // Check account exists
        if(!dao.checkAccountExists(accNum)) {
            throw new ServiceException(new ServiceError(Error.INVALID_ACCOUNT_NUMBER));
        }

        // Check for correct account pin
        Account account = dao.getAccount(accNum, accPin);
        if(account == null) {
            throw new ServiceException(new ServiceError(Error.INVALID_ACCOUNT_PIN));
        }

        // Check account has sufficient balance/overdraft funds
        if(account.totalWithdraw() < withdrawAmount) {
            throw new ServiceException(new ServiceError(Error.UNAVAILABLE_ACCOUNT_FUNDS));
        }

        // Check amount to be withdrawn is a multiple of 5
        if(withdrawAmount % 5 != 0) {
            throw new ServiceException(new ServiceError(Error.INVALID_AMOUNT_MULTIPLE));
        }

        ATM.setBankNotes(dao.getAtmBalance());

        // Check ATM has sufficient funds
        if(ATM.getAtmBalance() < withdrawAmount) {
            throw new ServiceException(new ServiceError(Error.UNAVAILABLE_ATM_FUNDS));
        }

        // Determine minimum notes to be withdrawn
        List<BankNote> notesToWithdraw = determineNotesToWithdraw(withdrawAmount);

        // Update ATM note count
        dao.withdrawFromAtm(notesToWithdraw);

        // Update Account balance
        dao.updateAccountBalance(account.getAccountNumber(), withdrawAmount);
        account.setOpeningBalance(account.getOpeningBalance() - withdrawAmount);

        WITHDRAWAL.setAccount(account);
        WITHDRAWAL.setBankNoteList(notesToWithdraw);

        LOGGER.info("Leaving updateAccountBalance in AtmService");

        return WITHDRAWAL;
    }

    /**
     * Determine what notes and how many of each to be withdrawn from the ATM
     *
     * @param withdrawAmount    amount to be withdrawn
     * @return List<BankNote>   list of bank notes to be withdrawn
     */
    private List<BankNote> determineNotesToWithdraw(int withdrawAmount) {
        List<BankNote> list = ATM.getBankNotes();
        List<BankNote> notesToWithdraw = new ArrayList<>();

        int amountLeft = withdrawAmount;

        // Make sure list sorted in descending order of note value
        list.sort((BankNote note1, BankNote note2) -> note2.getNoteAmount() - note1.getNoteAmount());

        for(BankNote bankNote : list) {

            int count = 0;

            // Check the amount left is greater than current note, and the notes of the current amount left
            while(amountLeft - bankNote.getNoteAmount() >=0 && bankNote.getNoteCount() > 0) {
                count++ ;
                amountLeft -= bankNote.getNoteAmount();
                bankNote.setNoteCount(bankNote.getNoteCount() - 1);
            }

            // Add the count of that note amount to withdraw to the list
            if(count != 0) {
                notesToWithdraw.add(new BankNote(bankNote.getNoteAmount(), count));
            }

            // Check if withdrawn enough
            if(amountLeft == 0)
                break;
        }

        return notesToWithdraw;
    }

    /**
     * Method to validate account parameters
     * - Validate that they're integers
     *
     * @param accountNumber account number to validate
     * @param pin           pin to validate
     */
    private void validateAccountParams(String accountNumber, String pin) {
        try {
            accNum = Integer.parseInt(accountNumber);
        } catch (NumberFormatException e) {
            LOGGER.error("Incorrect account number format supplied: {}", accountNumber);
            throw new ServiceException(new ServiceError(Error.INVALID_ACCOUNT_NUMBER_FORMAT));
        }

        try {
            accPin = Integer.parseInt(pin);
        } catch (NumberFormatException e) {
            LOGGER.error("Incorrect pin format supplied: {}", pin);
            throw new ServiceException(new ServiceError(Error.INVALID_PIN_FORMAT));
        }

    }

    /**
     * Method to validate withdrawal parameters
     * - Validate that they're integers
     *
     * @param accountNumber account number to validate
     * @param pin           pin to validate
     * @param amount        amount to validate
     */
    private void validateWithdrawParams(String accountNumber, String pin, String amount) {

        validateAccountParams(accountNumber, pin);

        try {
            withdrawAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            LOGGER.error("Incorrect amount format supplied: {}", amount);
            throw new ServiceException(new ServiceError(Error.INVALID_AMOUNT_FORMAT));
        }

    }

}
