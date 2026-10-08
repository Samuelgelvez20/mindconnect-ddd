package com.mindconnect.infrastructure.ai.chatairun.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateChatAiRunRequest(
        @NotNull
        UUID conversationId,

        @NotNull
        UUID messageId,

        @NotNull
        UUID modelId,

        @NotNull
        UUID aiRunStatusId) {
}