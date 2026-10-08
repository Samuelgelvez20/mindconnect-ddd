package com.mindconnect.domain.chat.priority.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidPriorityException extends DomainException {

    public InvalidPriorityException(String message) {
        super(message);
    }
}