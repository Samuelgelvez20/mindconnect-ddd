package com.mindconnect.domain.chat.chatescalationstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatEscalationStatusException extends DomainException {

    public InvalidChatEscalationStatusException(String message) {
        super(message);
    }
}