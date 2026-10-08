package com.mindconnect.infrastructure.chat.chatconversation.adapters.in.rest.dtos;

import java.time.Instant;
import jakarta.validation.constraints.NotNull;

public record UpdateChatConversationRequest(

        @NotNull(message = "conversationStatusId is required")
        java.util.UUID conversationStatusId,

        @NotNull(message = "priorityId is required")
        java.util.UUID priorityId,

        Instant lastMessageAt,

        boolean closed,

        Instant closedAt
) {
}