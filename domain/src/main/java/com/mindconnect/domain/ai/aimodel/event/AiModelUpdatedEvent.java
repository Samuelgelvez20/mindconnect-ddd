package com.mindconnect.domain.ai.aimodel.event;

import java.math.BigDecimal;
import java.time.Instant;

import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.common.event.DomainEvent;

public record AiModelUpdatedEvent(
        AiModelId aiModelId,
        AiProviderId aiProviderId,
        String name,
        String modelKey,
        BigDecimal inputTokenPrice,
        BigDecimal outputTokenPrice,
        int maxTokens,
        int contextWindow,
        boolean active,
        Instant updatedAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return updatedAt;
    }
}