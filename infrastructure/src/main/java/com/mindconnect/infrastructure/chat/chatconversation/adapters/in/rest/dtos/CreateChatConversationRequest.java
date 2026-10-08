package com.mindconnect.infrastructure.chat.chatconversation.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateChatConversationRequest(

        @NotNull(message = "conversationStatusId is required")
        java.util.UUID conversationStatusId,

        @NotNull(message = "priorityId is required")
        java.util.UUID priorityId
) {
}