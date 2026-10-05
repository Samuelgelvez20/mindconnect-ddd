package com.mindconnect.domain.patient.patient.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;

public record PatientDeletedEvent(PatientId id, Instant occurredOn) implements DomainEvent {

    public PatientDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}