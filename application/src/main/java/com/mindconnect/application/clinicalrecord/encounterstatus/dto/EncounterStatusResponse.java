package com.mindconnect.application.clinicalrecord.encounterstatus.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.encounterstatus.model.aggregate.EncounterStatus;

public record EncounterStatusResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static EncounterStatusResponse from(EncounterStatus status) {
        return new EncounterStatusResponse(
                status.id().value(),
                status.code(),
                status.name(),
                status.active(),
                status.createdAt(),
                status.updatedAt()
        );
    }
}