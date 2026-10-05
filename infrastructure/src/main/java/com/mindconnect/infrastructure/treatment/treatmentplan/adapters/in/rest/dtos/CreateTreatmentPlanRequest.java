package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTreatmentPlanRequest(

        @NotNull(message = "encounterId is required")
        String encounterId,

        @NotNull(message = "professionalId is required")
        String professionalId,

        @NotBlank(message = "title is required")
        @Size(max = 200, message = "title must have at most 200 characters")
        String title,

        String description,

        @NotNull(message = "startDate is required")
        String startDate,

        String endDate,

        @NotNull(message = "treatmentStatusId is required")
        String treatmentStatusId
) {
}