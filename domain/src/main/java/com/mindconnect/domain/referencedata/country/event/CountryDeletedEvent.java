package com.mindconnect.domain.referencedata.country.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record CountryDeletedEvent(CountryId id, Instant occurredOn) implements DomainEvent {

    public CountryDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
