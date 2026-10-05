package com.mindconnect.application.treatment.treatmentgoal.command;

public record UpdateTreatmentGoalCommand(
        String treatmentGoalId,
        String treatmentPlanId,
        String description,
        String targetDate,
        String treatmentGoalStatusId
) {
}