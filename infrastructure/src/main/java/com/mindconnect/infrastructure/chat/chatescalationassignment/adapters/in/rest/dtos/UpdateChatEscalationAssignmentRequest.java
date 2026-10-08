package com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateChatEscalationAssignmentRequest(

        @NotNull(message = "escalationId is required")
        java.util.UUID escalationId,

        @NotNull(message = "professionalId is required")
        java.util.UUID professionalId
) {
}