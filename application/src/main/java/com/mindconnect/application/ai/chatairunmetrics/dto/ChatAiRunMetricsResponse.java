package com.mindconnect.application.ai.chatairunmetrics.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public record ChatAiRunMetricsResponse(
        UUID id,
        UUID aiRunId,
        int promptTokens,
        int completionTokens,
        int totalTokens,
        BigDecimal cost,
        Instant createdAt) {

    public static ChatAiRunMetricsResponse from(ChatAiRunMetrics metrics) {
        return new ChatAiRunMetricsResponse(
                metrics.id().value(),
                metrics.aiRunId().value(),
                metrics.promptTokens(),
                metrics.completionTokens(),
                metrics.totalTokens(),
                metrics.cost(),
                metrics.createdAt());
    }
}