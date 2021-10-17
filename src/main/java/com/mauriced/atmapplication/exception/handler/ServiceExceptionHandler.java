package com.mauriced.atmapplication.exception.handler;

import com.mauriced.atmapplication.exception.ServiceException;
import com.mauriced.atmapplication.model.ServiceError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Handler class to handle service exceptions
 *
 * @author Maurice Danaher
 */
@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    protected ServiceError handleException(ServiceException e) {
        return e.getServiceError();

    }
}
