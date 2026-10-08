package com.mindconnect.domain.chat.chatescalationassignment.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatEscalationAssignmentException extends DomainException {

    public InvalidChatEscalationAssignmentException(String message) {
        super(message);
    }
}