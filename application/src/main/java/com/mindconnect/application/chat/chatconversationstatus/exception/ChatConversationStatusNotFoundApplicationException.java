package com.mindconnect.application.chat.chatconversationstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;

public class ChatConversationStatusNotFoundApplicationException extends NotFoundApplicationException {

    public ChatConversationStatusNotFoundApplicationException(ChatConversationStatusId id) {
        super("ChatConversationStatus not found with id: " + id.value());
    }
}