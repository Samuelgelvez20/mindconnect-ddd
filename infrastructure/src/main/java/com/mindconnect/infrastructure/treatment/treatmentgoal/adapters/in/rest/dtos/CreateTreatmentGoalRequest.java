package com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTreatmentGoalRequest(

        @NotNull(message = "treatmentPlanId is required")
        String treatmentPlanId,

        @NotBlank(message = "description is required")
        @Size(max = Integer.MAX_VALUE, message = "description must not be blank")
        String description,

        @NotNull(message = "targetDate is required")
        String targetDate,

        @NotNull(message = "treatmentGoalStatusId is required")
        String treatmentGoalStatusId
) {
}