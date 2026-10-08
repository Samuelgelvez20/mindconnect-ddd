package com.mindconnect.domain.referencedata.documenttype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public record DocumentTypeDeletedEvent(DocumentTypeId id, Instant occurredOn) implements DomainEvent {

    public DocumentTypeDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}