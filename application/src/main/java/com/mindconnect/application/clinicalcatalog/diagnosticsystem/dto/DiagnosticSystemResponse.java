package com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto;

import java.time.Instant;

public record DiagnosticSystemResponse(
        String id,
        String code,
        String name,
        String version,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static DiagnosticSystemResponse from(com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem aggregate) {
        return new DiagnosticSystemResponse(
                aggregate.id().value().toString(),
                aggregate.code(),
                aggregate.name(),
                aggregate.version(),
                aggregate.isActive(),
                aggregate.createdAt(),
                aggregate.updatedAt()
        );
    }
}