package com.mindconnect.domain.clinicalrecord.encounterstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

public record EncounterStatusUpdatedEvent(
        EncounterStatusId id,
        String code,
        String name,
        boolean active,
        Instant occurredOn
) implements DomainEvent {

    public EncounterStatusUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}