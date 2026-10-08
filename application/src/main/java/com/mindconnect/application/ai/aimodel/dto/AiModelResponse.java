package com.mindconnect.application.ai.aimodel.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

public record AiModelResponse(
        UUID id,
        UUID aiProviderId,
        String name,
        String modelKey,
        BigDecimal inputTokenPrice,
        BigDecimal outputTokenPrice,
        int maxTokens,
        int contextWindow,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static AiModelResponse from(AiModel model) {
        return new AiModelResponse(
                model.id().value(),
                model.aiProviderId().value(),
                model.name(),
                model.modelKey(),
                model.inputTokenPrice(),
                model.outputTokenPrice(),
                model.maxTokens(),
                model.contextWindow(),
                model.active(),
                model.createdAt(),
                model.updatedAt());
    }
}