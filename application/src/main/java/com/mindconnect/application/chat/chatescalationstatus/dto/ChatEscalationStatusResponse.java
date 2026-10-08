package com.mindconnect.application.chat.chatescalationstatus.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatescalationstatus.model.aggregate.ChatEscalationStatus;

public record ChatEscalationStatusResponse(
        UUID id,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatEscalationStatusResponse from(ChatEscalationStatus status) {
        return new ChatEscalationStatusResponse(
                status.id().value(),
                status.name(),
                true,
                status.createdAt(),
                status.updatedAt());
    }
}