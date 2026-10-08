package com.mindconnect.application.clinicalrecord.encountertype.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.encountertype.model.aggregate.EncounterType;

public record EncounterTypeResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static EncounterTypeResponse from(EncounterType type) {
        return new EncounterTypeResponse(
                type.id().value(),
                type.code(),
                type.name(),
                type.active(),
                type.createdAt(),
                type.updatedAt()
        );
    }
}