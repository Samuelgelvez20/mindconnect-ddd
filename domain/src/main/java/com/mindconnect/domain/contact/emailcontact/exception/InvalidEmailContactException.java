package com.mindconnect.domain.contact.emailcontact.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidEmailContactException extends DomainException {

    public InvalidEmailContactException(String message) {
        super(message);
    }
}