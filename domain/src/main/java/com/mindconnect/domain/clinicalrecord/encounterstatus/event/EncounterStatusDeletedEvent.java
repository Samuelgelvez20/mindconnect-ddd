package com.mindconnect.domain.clinicalrecord.encounterstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

public record EncounterStatusDeletedEvent(EncounterStatusId id, Instant occurredOn) implements DomainEvent {

    public EncounterStatusDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}