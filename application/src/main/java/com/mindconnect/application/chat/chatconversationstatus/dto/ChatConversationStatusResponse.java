package com.mindconnect.application.chat.chatconversationstatus.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatconversationstatus.model.aggregate.ChatConversationStatus;

public record ChatConversationStatusResponse(
        UUID id,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatConversationStatusResponse from(ChatConversationStatus status) {
        return new ChatConversationStatusResponse(
                status.id().value(),
                status.name(),
                true,
                status.createdAt(),
                status.updatedAt());
    }
}