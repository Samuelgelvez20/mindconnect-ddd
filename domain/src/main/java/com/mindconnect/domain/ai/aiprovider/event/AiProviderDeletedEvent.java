package com.mindconnect.domain.ai.aiprovider.event;

import java.time.Instant;

import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.common.event.DomainEvent;

public record AiProviderDeletedEvent(
        AiProviderId aiProviderId,
        Instant occurredOn) implements DomainEvent {
}