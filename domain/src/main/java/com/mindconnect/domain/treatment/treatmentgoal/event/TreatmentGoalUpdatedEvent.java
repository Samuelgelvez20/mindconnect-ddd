package com.mindconnect.domain.treatment.treatmentgoal.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;

public record TreatmentGoalUpdatedEvent(
        TreatmentGoalId id,
        TreatmentPlanId treatmentPlanId,
        String description,
        LocalDate targetDate,
        TreatmentGoalStatusId treatmentGoalStatusId,
        Instant occurredOn) implements DomainEvent {

    public TreatmentGoalUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}