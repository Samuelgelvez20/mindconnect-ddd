package com.mindconnect.domain.clinicalcatalog.consenttype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidConsentTypeException extends DomainException {

    public InvalidConsentTypeException(String message) {
        super(message);
    }
}