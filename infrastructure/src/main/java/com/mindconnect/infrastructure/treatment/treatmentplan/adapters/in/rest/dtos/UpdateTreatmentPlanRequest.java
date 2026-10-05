package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTreatmentPlanRequest(

        String encounterId,

        String professionalId,

        @NotBlank(message = "title is required")
        @Size(max = 200, message = "title must have at most 200 characters")
        String title,

        String description,

        String startDate,

        String endDate,

        String treatmentStatusId
) {
}