package com.mindconnect.domain.treatment.treatmentgoalstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;

public record TreatmentGoalStatusRegisteredEvent(TreatmentGoalStatusId id, Instant occurredOn) implements DomainEvent {

    public TreatmentGoalStatusRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}