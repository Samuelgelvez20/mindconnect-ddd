package com.mindconnect.application.treatment.treatmentgoal.command;

public record RegisterTreatmentGoalCommand(
        String treatmentPlanId,
        String description,
        String targetDate,
        String treatmentGoalStatusId
) {
}