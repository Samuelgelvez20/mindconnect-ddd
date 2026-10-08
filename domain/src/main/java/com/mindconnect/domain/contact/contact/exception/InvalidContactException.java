package com.mindconnect.domain.contact.contact.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidContactException extends DomainException {

    public InvalidContactException(String message) {
        super(message);
    }
}