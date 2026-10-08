package com.mindconnect.domain.chat.chatconversation.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatConversationException extends DomainException {

    public InvalidChatConversationException(String message) {
        super(message);
    }
}