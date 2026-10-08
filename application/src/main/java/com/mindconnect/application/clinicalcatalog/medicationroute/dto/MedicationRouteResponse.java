package com.mindconnect.application.clinicalcatalog.medicationroute.dto;

import java.time.Instant;

public record MedicationRouteResponse(
        String id,
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static MedicationRouteResponse from(com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute aggregate) {
        return new MedicationRouteResponse(
                aggregate.id().value().toString(),
                aggregate.code(),
                aggregate.name(),
                aggregate.isActive(),
                aggregate.createdAt(),
                aggregate.updatedAt()
        );
    }
}