package com.mindconnect.application.chat.chatescalationassignment.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RegisterChatEscalationAssignmentCommand(
        ChatEscalationId escalationId,
        ProfessionalId professionalId) {

    public RegisterChatEscalationAssignmentCommand {
        Objects.requireNonNull(escalationId, "escalationId must not be null");
        Objects.requireNonNull(professionalId, "professionalId must not be null");
    }
}