package com.mindconnect.domain.ai.chatairunmetrics.event;

import java.math.BigDecimal;
import java.time.Instant;

import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunMetricsUpdatedEvent(
        ChatAiRunMetricsId chatAiRunMetricsId,
        ChatAiRunId aiRunId,
        int promptTokens,
        int completionTokens,
        int totalTokens,
        BigDecimal cost,
        Instant createdAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return createdAt;
    }
}