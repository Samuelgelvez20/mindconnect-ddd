package com.mindconnect.application.clinicalcatalog.consenttype.dto;

import java.time.Instant;

public record ConsentTypeResponse(
        String id,
        String code,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static ConsentTypeResponse from(com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType aggregate) {
        return new ConsentTypeResponse(
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