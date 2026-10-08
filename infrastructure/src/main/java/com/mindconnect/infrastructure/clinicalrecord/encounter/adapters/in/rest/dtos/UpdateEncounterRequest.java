package com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateEncounterRequest(

        @NotNull(message = "clinicalRecordId is required")
        java.util.UUID clinicalRecordId,

        @NotNull(message = "professionalId is required")
        java.util.UUID professionalId,

        @NotNull(message = "encounterTypeId is required")
        java.util.UUID encounterTypeId,

        @NotNull(message = "startedAt is required")
        java.time.Instant startedAt,

        java.time.Instant endedAt,

        String reasonForVisit,

        String currentCondition,

        @NotNull(message = "modalityId is required")
        java.util.UUID modalityId,

        @NotNull(message = "statusId is required")
        java.util.UUID statusId,

        @NotNull(message = "updatedBy is required")
        java.util.UUID updatedBy
) {
}