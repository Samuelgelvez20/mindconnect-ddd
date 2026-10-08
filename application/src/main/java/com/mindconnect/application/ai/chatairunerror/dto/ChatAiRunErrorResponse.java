package com.mindconnect.application.ai.chatairunerror.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public record ChatAiRunErrorResponse(
        UUID id,
        UUID aiRunId,
        String errorMessage,
        String errorCode,
        String providerErrorId,
        Instant createdAt) {

    public static ChatAiRunErrorResponse from(ChatAiRunError error) {
        return new ChatAiRunErrorResponse(
                error.id().value(),
                error.aiRunId().value(),
                error.errorMessage(),
                error.errorCode(),
                error.providerErrorId(),
                error.createdAt());
    }
}