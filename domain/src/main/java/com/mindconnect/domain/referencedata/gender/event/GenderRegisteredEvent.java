package com.mindconnect.domain.referencedata.gender.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public record GenderRegisteredEvent(GenderId id, Instant occurredOn) implements DomainEvent {

    public GenderRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}