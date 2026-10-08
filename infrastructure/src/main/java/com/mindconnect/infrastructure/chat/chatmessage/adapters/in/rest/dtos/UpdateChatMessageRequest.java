package com.mindconnect.infrastructure.chat.chatmessage.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateChatMessageRequest(

        @NotNull(message = "conversationId is required")
        java.util.UUID conversationId,

        @NotNull(message = "messageTypeId is required")
        java.util.UUID messageTypeId,

        @NotNull(message = "participantId is required")
        java.util.UUID participantId,

        @NotBlank(message = "content is required")
        String content,

        String metadata
) {
}