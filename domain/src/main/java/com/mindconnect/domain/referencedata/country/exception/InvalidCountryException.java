package com.mindconnect.domain.referencedata.country.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidCountryException extends DomainException {

    public InvalidCountryException(String message) {
        super(message);
    }
}
