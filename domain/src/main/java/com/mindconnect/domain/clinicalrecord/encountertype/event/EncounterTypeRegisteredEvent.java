package com.mindconnect.domain.clinicalrecord.encountertype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;

public record EncounterTypeRegisteredEvent(EncounterTypeId id, Instant occurredOn) implements DomainEvent {

    public EncounterTypeRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}