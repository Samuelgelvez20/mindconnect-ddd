package com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateClinicalRecordRequest(

        @NotNull(message = "patientId is required")
        java.util.UUID patientId,

        @NotBlank(message = "recordNumber is required")
        @Size(max = 50, message = "recordNumber must have at most 50 characters")
        String recordNumber,

        java.time.Instant openedAt,

        java.time.Instant closedAt,

        @NotNull(message = "statusId is required")
        java.util.UUID statusId
) {
}