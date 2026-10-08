package com.mindconnect.domain.chat.chatconversationstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatConversationStatusException extends DomainException {

    public InvalidChatConversationStatusException(String message) {
        super(message);
    }
}