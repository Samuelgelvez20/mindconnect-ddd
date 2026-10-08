package com.mindconnect.application.chat.sendertype.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.sendertype.model.aggregate.SenderType;

public record SenderTypeResponse(
        UUID id,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static SenderTypeResponse from(SenderType senderType) {
        return new SenderTypeResponse(
                senderType.id().value(),
                senderType.name(),
                true,
                senderType.createdAt(),
                senderType.updatedAt());
    }
}