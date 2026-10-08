package com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.in.rest.dtos;

import java.time.Instant;
import jakarta.validation.constraints.NotNull;

public record CreateChatEscalationStatusHistoryRequest(

        @NotNull(message = "escalationId is required")
        java.util.UUID escalationId,

        @NotNull(message = "escalationStatusId is required")
        java.util.UUID escalationStatusId,

        @NotNull(message = "changedAt is required")
        Instant changedAt
) {
}