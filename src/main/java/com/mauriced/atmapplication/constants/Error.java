package com.mauriced.atmapplication.constants;

/**
 * Error enum class containing possible error codes and messages
 *
 * @author Maurice Danaher
 */
public enum Error {

    GENERAL_ERROR("2000", "General Error occurred"),
    INVALID_ACCOUNT_NUMBER_FORMAT("2001", "Invalid account number format"),
    INVALID_PIN_FORMAT("2002", "Invalid pin format"),
    INVALID_AMOUNT_FORMAT("2003", "Invalid amount format"),
    INVALID_AMOUNT_MULTIPLE("2004", "The amount value must be a multiple of 5"),
    INVALID_ACCOUNT_NUMBER("3001", "No accounts found"),
    INVALID_ACCOUNT_PIN("4001", "Incorrect pin"),

    UNAVAILABLE_ACCOUNT_FUNDS("5001", "Account balance too low to withdraw this amount"),
    UNAVAILABLE_ATM_FUNDS("5002", "ATM balance too low to withdraw this amount");



    private String errorCode;
    private String errorMessage;

    Error(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
