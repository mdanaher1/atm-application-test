package com.mauriced.atmapplication.dao;

import com.mauriced.atmapplication.model.Account;
import com.mauriced.atmapplication.model.BankNote;

import java.util.List;

/**
 * Dao interface class for accessing ATM DB
 *
 * @author Maurice Danaher
 */
public interface AtmDao {

    /**
     *
     * @param accountNumber
     * @return
     */
    Account getAccount(int accountNumber, int pin);

    /**
     * Check for account number in ATM DB
     *
     * @param accountNumber account number to check
     * @return boolean      true if account number exists, else false
     */
    boolean checkAccountExists(int accountNumber);

    /**
     * Update balance of account after withdrawal
     * @param account   account from which to updateAccountBalance from
     * @param amount    amount to be withdrawn
     * @return Withdraw
     */
    void updateAccountBalance(int account, int amount);

    /**
     *
     */
    List<BankNote> getAtmBalance();

    /**
     * Withdraw notes from the ATM
     * @param bankNotes list of bank notes to be withdrawn from ATM
     * @return Withdraw
     */
    void withdrawFromAtm(List<BankNote> bankNotes);
}
