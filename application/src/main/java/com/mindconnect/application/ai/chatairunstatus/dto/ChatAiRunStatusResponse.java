package com.mindconnect.application.ai.chatairunstatus.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

public record ChatAiRunStatusResponse(
        UUID id,
        String name,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatAiRunStatusResponse from(ChatAiRunStatus status) {
        return new ChatAiRunStatusResponse(
                status.id().value(),
                status.name(),
                status.createdAt(),
                status.updatedAt());
    }
}