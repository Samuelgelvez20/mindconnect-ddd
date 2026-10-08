package com.mindconnect.domain.clinicalcatalog.medicationroute.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;

public record MedicationRouteDeletedEvent(MedicationRouteId id, Instant occurredOn) implements DomainEvent {

    public MedicationRouteDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}