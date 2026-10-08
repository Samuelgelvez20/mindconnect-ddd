package com.mindconnect.application.treatment.treatmentplan.command;

public record RegisterTreatmentPlanCommand(
        String encounterId,
        String professionalId,
        String title,
        String description,
        String startDate,
        String endDate,
        String treatmentStatusId
) {
}