package com.mindconnect.domain.treatment.treatmentplan.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;

public record TreatmentPlanDeletedEvent(TreatmentPlanId id, Instant occurredOn) implements DomainEvent {

    public TreatmentPlanDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}