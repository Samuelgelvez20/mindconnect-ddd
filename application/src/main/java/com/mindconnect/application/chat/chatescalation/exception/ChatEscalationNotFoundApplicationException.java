package com.mindconnect.application.chat.chatescalation.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;

public class ChatEscalationNotFoundApplicationException extends NotFoundApplicationException {

    public ChatEscalationNotFoundApplicationException(ChatEscalationId id) {
        super("ChatEscalation not found with id: " + id.value());
    }
}