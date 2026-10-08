package com.mindconnect.infrastructure.chat.chatescalation.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateChatEscalationRequest(

        @NotNull(message = "conversationId is required")
        java.util.UUID conversationId,

        @NotNull(message = "statusId is required")
        java.util.UUID statusId,

        boolean fromAi,

        String reason
) {
}