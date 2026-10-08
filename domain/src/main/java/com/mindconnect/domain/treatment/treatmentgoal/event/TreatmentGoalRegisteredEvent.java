package com.mindconnect.domain.treatment.treatmentgoal.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;

public record TreatmentGoalRegisteredEvent(TreatmentGoalId id, Instant occurredOn) implements DomainEvent {

    public TreatmentGoalRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}