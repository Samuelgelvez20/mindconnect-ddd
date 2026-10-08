package com.mindconnect.domain.chat.chatescalationstatushistory.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatEscalationStatusHistoryException extends DomainException {

    public InvalidChatEscalationStatusHistoryException(String message) {
        super(message);
    }
}