package com.mindconnect.domain.clinicalcatalog.consenttype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;

public record ConsentTypeDeletedEvent(ConsentTypeId id, Instant occurredOn) implements DomainEvent {

    public ConsentTypeDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}