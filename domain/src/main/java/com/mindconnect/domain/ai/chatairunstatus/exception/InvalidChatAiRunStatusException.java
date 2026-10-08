package com.mindconnect.domain.ai.chatairunstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatAiRunStatusException extends DomainException {

    public InvalidChatAiRunStatusException(String message) {
        super(message);
    }
}