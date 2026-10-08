package com.mindconnect.domain.ai.chatairunmetrics.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunMetricsRegisteredEvent(
        ChatAiRunMetricsId chatAiRunMetricsId,
        Instant occurredOn) implements DomainEvent {
}