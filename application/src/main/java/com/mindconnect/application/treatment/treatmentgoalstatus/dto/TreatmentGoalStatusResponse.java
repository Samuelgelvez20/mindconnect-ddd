package com.mindconnect.application.treatment.treatmentgoalstatus.dto;

import java.time.Instant;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;

public record TreatmentGoalStatusResponse(
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static TreatmentGoalStatusResponse from(TreatmentGoalStatus aggregate) {
        return new TreatmentGoalStatusResponse(
                aggregate.code(),
                aggregate.name(),
                aggregate.isActive(),
                aggregate.createdAt(),
                aggregate.updatedAt()
        );
    }
}