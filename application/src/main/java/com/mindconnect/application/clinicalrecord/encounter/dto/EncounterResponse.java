package com.mindconnect.application.clinicalrecord.encounter.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;

public record EncounterResponse(
        UUID id,
        UUID clinicalRecordId,
        UUID professionalId,
        UUID encounterTypeId,
        Instant startedAt,
        Instant endedAt,
        String reasonForVisit,
        String currentCondition,
        UUID modalityId,
        UUID statusId,
        UUID createdBy,
        UUID updatedBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static EncounterResponse from(Encounter encounter) {
        return new EncounterResponse(
                encounter.id().value(),
                encounter.clinicalRecordId().value(),
                encounter.professionalId().value(),
                encounter.encounterTypeId().value(),
                encounter.startedAt(),
                encounter.endedAt(),
                encounter.reasonForVisit(),
                encounter.currentCondition(),
                encounter.modalityId().value(),
                encounter.statusId().value(),
                encounter.createdBy().value(),
                encounter.updatedBy() != null ? encounter.updatedBy().value() : null,
                encounter.createdAt(),
                encounter.updatedAt()
        );
    }
}