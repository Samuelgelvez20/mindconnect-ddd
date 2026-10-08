package com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateClinicalNoteRequest(

        @NotNull(message = "encounterId is required")
        java.util.UUID encounterId,

        @NotNull(message = "professionalId is required")
        java.util.UUID professionalId,

        String subjective,
        String objective,
        String assessment,
        String plan,
        String additionalNotes,
        java.time.Instant signedAt
) {
}