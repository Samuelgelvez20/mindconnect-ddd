package com.mindconnect.domain.contact.phonecontact.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidPhoneContactException extends DomainException {

    public InvalidPhoneContactException(String message) {
        super(message);
    }
}