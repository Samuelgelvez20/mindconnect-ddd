package com.mindconnect.application.chat.chatescalationstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class ChatEscalationStatusNotFoundApplicationException extends NotFoundApplicationException {

    public ChatEscalationStatusNotFoundApplicationException(ChatEscalationStatusId id) {
        super("ChatEscalationStatus not found with id: " + id.value());
    }
}