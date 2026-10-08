package com.mindconnect.domain.clinicalrecord.encounter.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;

public record EncounterRegisteredEvent(EncounterId id, Instant occurredOn) implements DomainEvent {

    public EncounterRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}