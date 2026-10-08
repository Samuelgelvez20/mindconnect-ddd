package com.mindconnect.application.ai.chatairunmetrics.command;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateChatAiRunMetricsCommand(
        UUID id,
        UUID aiRunId,
        int promptTokens,
        int completionTokens,
        int totalTokens,
        BigDecimal cost) {
}