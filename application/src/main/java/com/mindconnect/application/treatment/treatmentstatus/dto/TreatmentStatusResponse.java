package com.mindconnect.application.treatment.treatmentstatus.dto;

import java.time.Instant;
import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;

public record TreatmentStatusResponse(
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static TreatmentStatusResponse from(TreatmentStatus aggregate) {
        return new TreatmentStatusResponse(
                aggregate.code(),
                aggregate.name(),
                aggregate.isActive(),
                aggregate.createdAt(),
                aggregate.updatedAt()
        );
    }
}