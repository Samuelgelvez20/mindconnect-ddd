package com.mindconnect.application.chat.chatmessage.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;

public class ChatMessageNotFoundApplicationException extends NotFoundApplicationException {

    public ChatMessageNotFoundApplicationException(ChatMessageId id) {
        super("ChatMessage not found with id: " + id.value());
    }
}