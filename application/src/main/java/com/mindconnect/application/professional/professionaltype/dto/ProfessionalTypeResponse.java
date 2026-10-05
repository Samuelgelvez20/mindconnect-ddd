package com.mindconnect.application.professional.professionaltype.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;

public record ProfessionalTypeResponse(
        UUID id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProfessionalTypeResponse from(ProfessionalType professionalType) {
        return new ProfessionalTypeResponse(
                professionalType.id().value(),
                professionalType.name(),
                professionalType.createdAt(),
                professionalType.updatedAt()
        );
    }
}