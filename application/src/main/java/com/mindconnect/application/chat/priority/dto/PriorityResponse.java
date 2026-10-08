package com.mindconnect.application.chat.priority.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.priority.model.aggregate.Priority;

public record PriorityResponse(
        UUID id,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static PriorityResponse from(Priority priority) {
        return new PriorityResponse(
                priority.id().value(),
                priority.name(),
                true,
                priority.createdAt(),
                priority.updatedAt());
    }
}