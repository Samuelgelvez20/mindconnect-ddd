package com.mindconnect.application.ai.aimodel.command;

import java.math.BigDecimal;
import java.util.UUID;

public record RegisterAiModelCommand(
        UUID aiProviderId,
        String name,
        String modelKey,
        BigDecimal inputTokenPrice,
        BigDecimal outputTokenPrice,
        int maxTokens,
        int contextWindow) {
}