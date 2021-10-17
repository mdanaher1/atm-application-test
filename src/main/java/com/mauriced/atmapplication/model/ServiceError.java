package com.mauriced.atmapplication.model;

import com.mauriced.atmapplication.constants.Error;

/**
 * ServiceError class
 *
 * @author Maurice Danaher
 */
public class ServiceError {

    private String errorCode;
    private String errorMessage;

    public ServiceError(Error error) {
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
