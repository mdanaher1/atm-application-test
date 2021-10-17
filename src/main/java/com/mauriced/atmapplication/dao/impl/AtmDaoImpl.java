package com.mauriced.atmapplication.dao.impl;

import com.mauriced.atmapplication.dao.AtmDao;
import com.mauriced.atmapplication.model.Account;
import com.mauriced.atmapplication.model.BankNote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Dao class for accessing ATM DB
 *
 * @author Maurice Danaher
 */
@Repository
public class AtmDaoImpl implements AtmDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(AtmDaoImpl.class);

    private static final String SQL_ACCOUNTS_GET_ACCOUNT = "SELECT * FROM accounts WHERE account_number = ? and pin = ?";

    private static final String SQL_ACCOUNTS_CHECK_ACCOUNT_EXISTS = "SELECT count(*) FROM accounts WHERE account_number = ?";

    private static final String SQL_ACCOUNTS_UPDATE_BALANCE = "UPDATE accounts SET opening_balance = opening_balance - ? WHERE account_number = ?";

    private static final String SQL_ATM_BALANCE = "SELECT * FROM atm ";

    private static final String SQL_ATM_UPDATE_NOTE_COUNT = "UPDATE atm SET note_count = note_count - ? WHERE note_amount = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Retrieve account details
     *
     * @param accountNumber account number of account to retrieve
     * @param pin           pin of the account to retrieve
     * @return Account      account details
     */
    @Override
    public Account getAccount(int accountNumber, int pin) {
        LOGGER.info("Retrieving account details for {}", accountNumber);
        Account account;
        try {
            account = jdbcTemplate.queryForObject(SQL_ACCOUNTS_GET_ACCOUNT, new Object[]{accountNumber, pin}, new AccountMapper());
        } catch (EmptyResultDataAccessException e) {
            account = null;
        }

        return account;
    }

    /**
     * Check that the account number exists in the DB
     *
     * @param accountNumber account number to check
     * @return boolean      true if account exists, else false
     */
    @Override
    public boolean checkAccountExists(int accountNumber) {
        LOGGER.info("Checking for existence of account number: {}", accountNumber);
        int count = jdbcTemplate.queryForObject(SQL_ACCOUNTS_CHECK_ACCOUNT_EXISTS, Integer.class, accountNumber);
        return count == 1;
    }

    /**
     * Update the balance of an account
     * @param accountNumber account number of account who's balance to update
     * @param amount        amount by which to reduce balance
     */
    @Override
    public void updateAccountBalance(int accountNumber, int amount) {
        LOGGER.info("Updating the balance of account {}", accountNumber);
        jdbcTemplate.update(SQL_ACCOUNTS_UPDATE_BALANCE, amount, accountNumber);
    }

    /**
     * Retrieve the count of notes in the amount
     *
     * @return List<BankNote>   List of BankNotes
     */
    @Override
    public List<BankNote> getAtmBalance() {
        LOGGER.info("Retrieving ATM balance");
        return jdbcTemplate.query(SQL_ATM_BALANCE, new BankNoteMapper());
    }

    /**
     * Update the note count of each bank note in the ATM
     *
     * @param bankNotes list of bank notes to be withdrawn from ATM
     */
    @Override
    public void withdrawFromAtm(List<BankNote> bankNotes) {
        LOGGER.info("Updating note count in ATM");
        for (BankNote bankNote:bankNotes) {
            if(bankNote.getNoteCount() != 0) {
                jdbcTemplate.update(SQL_ATM_UPDATE_NOTE_COUNT, bankNote.getNoteCount(), bankNote.getNoteAmount());
            }
        }
    }

    /**
     * Custom mapper for mapping query result to Account object
     */
    public class AccountMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();

            account.setAccountNumber(rs.getInt("account_number"));
            account.setOpeningBalance(rs.getInt("opening_balance"));
            account.setOverdraft(rs.getInt("overdraft"));

            return account;
        }
    }

    /**
     * Custom mapper for mapping query result to BankNote object
     */
    public class BankNoteMapper implements RowMapper<BankNote> {
        @Override
        public BankNote mapRow(ResultSet rs, int rowNum) throws SQLException {
            BankNote bankNote = new BankNote();

            bankNote.setNoteAmount(rs.getInt("note_amount"));
            bankNote.setNoteCount(rs.getInt("note_count"));

            return bankNote;
        }
    }
    
}
