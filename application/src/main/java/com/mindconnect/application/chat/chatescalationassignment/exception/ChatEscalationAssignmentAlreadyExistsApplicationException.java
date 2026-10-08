package com.mindconnect.application.chat.chatescalationassignment.exception;

import java.util.UUID;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ChatEscalationAssignmentAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ChatEscalationAssignmentAlreadyExistsApplicationException(UUID escalationId, UUID professionalId) {
        super("ChatEscalationAssignment already exists for escalation: " + escalationId + " and professional: " + professionalId);
    }
}