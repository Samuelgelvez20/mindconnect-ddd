package com.mindconnect.domain.chat.sendertype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidSenderTypeException extends DomainException {

    public InvalidSenderTypeException(String message) {
        super(message);
    }
}