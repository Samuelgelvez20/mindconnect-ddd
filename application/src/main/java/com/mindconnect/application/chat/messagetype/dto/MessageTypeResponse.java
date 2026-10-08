package com.mindconnect.application.chat.messagetype.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.messagetype.model.aggregate.MessageType;

public record MessageTypeResponse(
        UUID id,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static MessageTypeResponse from(MessageType messageType) {
        return new MessageTypeResponse(
                messageType.id().value(),
                messageType.name(),
                true,
                messageType.createdAt(),
                messageType.updatedAt());
    }
}