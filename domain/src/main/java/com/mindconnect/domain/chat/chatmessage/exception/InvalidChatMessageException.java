package com.mindconnect.domain.chat.chatmessage.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatMessageException extends DomainException {

    public InvalidChatMessageException(String message) {
        super(message);
    }
}