package com.mindconnect.application.clinicalcatalog.assessmenttype.dto;

import java.time.Instant;

public record AssessmentTypeResponse(
        String id,
        String code,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static AssessmentTypeResponse from(com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType aggregate) {
        return new AssessmentTypeResponse(
                aggregate.id().value().toString(),
                aggregate.code(),
                aggregate.name(),
                aggregate.description(),
                aggregate.isActive(),
                aggregate.createdAt(),
                aggregate.updatedAt()
        );
    }
}