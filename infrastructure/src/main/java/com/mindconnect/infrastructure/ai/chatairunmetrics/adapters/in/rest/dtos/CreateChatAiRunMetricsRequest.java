package com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.in.rest.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateChatAiRunMetricsRequest(
        @NotNull
        UUID aiRunId,

        @NotNull
        Integer promptTokens,

        @NotNull
        Integer completionTokens,

        @NotNull
        Integer totalTokens,

        @NotNull
        BigDecimal cost) {
}