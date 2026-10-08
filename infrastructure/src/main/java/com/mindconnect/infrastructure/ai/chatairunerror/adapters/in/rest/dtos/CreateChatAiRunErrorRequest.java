package com.mindconnect.infrastructure.ai.chatairunerror.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateChatAiRunErrorRequest(
        @NotNull
        UUID aiRunId,

        @NotBlank
        String errorMessage,

        String errorCode,

        String providerErrorId) {
}