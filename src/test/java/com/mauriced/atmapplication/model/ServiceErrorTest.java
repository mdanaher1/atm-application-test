package com.mauriced.atmapplication.model;

import com.mauriced.atmapplication.constants.Error;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Maurice Danaher
 */
public class ServiceErrorTest {

    @Test
    public void testSettersAndGetters() {

        ServiceError serviceError = new ServiceError(Error.GENERAL_ERROR);

        assertEquals(Error.GENERAL_ERROR.getErrorCode(), serviceError.getErrorCode());
        assertEquals(Error.GENERAL_ERROR.getErrorMessage(), serviceError.getErrorMessage());

        serviceError.setErrorCode(Error.INVALID_ACCOUNT_NUMBER.getErrorCode());
        serviceError.setErrorMessage(Error.INVALID_ACCOUNT_NUMBER.getErrorMessage());

        assertEquals(Error.INVALID_ACCOUNT_NUMBER.getErrorCode(), serviceError.getErrorCode());
        assertEquals(Error.INVALID_ACCOUNT_NUMBER.getErrorMessage(), serviceError.getErrorMessage());
    }

}