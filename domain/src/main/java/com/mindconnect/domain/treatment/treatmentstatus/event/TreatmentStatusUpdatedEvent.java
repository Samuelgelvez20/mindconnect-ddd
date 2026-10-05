package com.mindconnect.domain.treatment.treatmentstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;

public record TreatmentStatusUpdatedEvent(TreatmentStatusId id, String code, String name, Instant occurredOn) implements DomainEvent {

    public TreatmentStatusUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}