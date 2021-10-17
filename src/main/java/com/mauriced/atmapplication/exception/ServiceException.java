package com.mauriced.atmapplication.exception;

import com.mauriced.atmapplication.model.ServiceError;

/**
 * Custom Exception class
 *
 * @author Maurice Danaher
 */
public class ServiceException extends RuntimeException{

    private final transient ServiceError serviceError;

    public ServiceException(ServiceError serviceError) {
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return this.serviceError;
    }

}