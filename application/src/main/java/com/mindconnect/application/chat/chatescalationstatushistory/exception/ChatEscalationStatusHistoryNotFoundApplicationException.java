package com.mindconnect.application.chat.chatescalationstatushistory.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;

public class ChatEscalationStatusHistoryNotFoundApplicationException extends NotFoundApplicationException {

    public ChatEscalationStatusHistoryNotFoundApplicationException(ChatEscalationStatusHistoryId id) {
        super("ChatEscalationStatusHistory not found with id: " + id.value());
    }
}