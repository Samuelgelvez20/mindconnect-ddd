package com.mindconnect.domain.chat.messagetype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidMessageTypeException extends DomainException {

    public InvalidMessageTypeException(String message) {
        super(message);
    }
}