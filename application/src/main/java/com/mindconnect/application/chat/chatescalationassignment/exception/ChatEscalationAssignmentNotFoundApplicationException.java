package com.mindconnect.application.chat.chatescalationassignment.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;

public class ChatEscalationAssignmentNotFoundApplicationException extends NotFoundApplicationException {

    public ChatEscalationAssignmentNotFoundApplicationException(ChatEscalationAssignmentId id) {
        super("ChatEscalationAssignment not found with id: " + id.value());
    }
}