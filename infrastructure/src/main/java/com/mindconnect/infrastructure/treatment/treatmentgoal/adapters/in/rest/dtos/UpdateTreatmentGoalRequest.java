package com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTreatmentGoalRequest(

        String treatmentPlanId,

        @NotBlank(message = "description is required")
        @Size(max = Integer.MAX_VALUE, message = "description must not be blank")
        String description,

        String targetDate,

        String treatmentGoalStatusId
) {
}