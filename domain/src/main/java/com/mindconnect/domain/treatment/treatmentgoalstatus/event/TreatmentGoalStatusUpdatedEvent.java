package com.mindconnect.domain.treatment.treatmentgoalstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;

public record TreatmentGoalStatusUpdatedEvent(TreatmentGoalStatusId id, String code, String name, Instant occurredOn) implements DomainEvent {

    public TreatmentGoalStatusUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}