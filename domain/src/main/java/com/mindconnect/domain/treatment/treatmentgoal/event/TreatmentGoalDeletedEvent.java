package com.mindconnect.domain.treatment.treatmentgoal.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;

public record TreatmentGoalDeletedEvent(TreatmentGoalId id, Instant occurredOn) implements DomainEvent {

    public TreatmentGoalDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}