package com.mindconnect.infrastructure.ai.aimodel.adapters.in.rest.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAiModelRequest(
        @NotNull
        UUID aiProviderId,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 120)
        String modelKey,

        @NotNull
        BigDecimal inputTokenPrice,

        @NotNull
        BigDecimal outputTokenPrice,

        @NotNull
        Integer maxTokens,

        @NotNull
        Integer contextWindow,

        @NotNull
        boolean active) {
}