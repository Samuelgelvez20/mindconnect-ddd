package com.mindconnect.domain.chat.chatescalation.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatEscalationException extends DomainException {

    public InvalidChatEscalationException(String message) {
        super(message);
    }
}