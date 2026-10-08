package com.mindconnect.domain.treatment.treatmentstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;

public record TreatmentStatusRegisteredEvent(TreatmentStatusId id, Instant occurredOn) implements DomainEvent {

    public TreatmentStatusRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}