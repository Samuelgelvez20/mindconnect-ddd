package com.mindconnect.domain.clinicalrecord.encountermodality.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;

public record EncounterModalityDeletedEvent(EncounterModalityId id, Instant occurredOn) implements DomainEvent {

    public EncounterModalityDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}