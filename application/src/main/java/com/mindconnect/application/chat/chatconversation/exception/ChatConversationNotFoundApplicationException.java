package com.mindconnect.application.chat.chatconversation.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;

public class ChatConversationNotFoundApplicationException extends NotFoundApplicationException {

    public ChatConversationNotFoundApplicationException(ChatConversationId id) {
        super("ChatConversation not found with id: " + id.value());
    }
}