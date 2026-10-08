package com.mindconnect.application.clinicalrecord.encountermodality.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate.EncounterModality;

public record EncounterModalityResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static EncounterModalityResponse from(EncounterModality modality) {
        return new EncounterModalityResponse(
                modality.id().value(),
                modality.code(),
                modality.name(),
                modality.active(),
                modality.createdAt(),
                modality.updatedAt()
        );
    }
}