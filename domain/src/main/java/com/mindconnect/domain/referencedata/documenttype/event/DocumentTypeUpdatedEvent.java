package com.mindconnect.domain.referencedata.documenttype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public record DocumentTypeUpdatedEvent(
        DocumentTypeId id,
        String code,
        String name,
        Instant occurredOn
) implements DomainEvent {

    public DocumentTypeUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}