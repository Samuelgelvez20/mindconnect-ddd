package com.mindconnect.domain.ai.aiprovider.event;

import java.time.Instant;

import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.common.event.DomainEvent;

public record AiProviderUpdatedEvent(
        AiProviderId aiProviderId,
        String name,
        String legalName,
        String website,
        boolean active,
        Instant updatedAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return updatedAt;
    }
}