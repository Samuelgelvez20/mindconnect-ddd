package com.mindconnect.domain.ai.aimodel.event;

import java.time.Instant;

import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.common.event.DomainEvent;

public record AiModelRegisteredEvent(
        AiModelId aiModelId,
        Instant occurredOn) implements DomainEvent {
}