package com.mindconnect.application.treatment.treatmentplan.dto;

public record TreatmentPlanResponse(
        String id,
        String encounterId,
        String professionalId,
        String title,
        String description,
        String startDate,
        String endDate,
        String treatmentStatusId,
        boolean active,
        String createdAt,
        String updatedAt
) {

    public static TreatmentPlanResponse from(com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan aggregate) {
        return new TreatmentPlanResponse(
                aggregate.id().value().toString(),
                aggregate.encounterId().value().toString(),
                aggregate.professionalId().value().toString(),
                aggregate.title(),
                aggregate.description(),
                aggregate.startDate().toString(),
                aggregate.endDate().toString(),
                aggregate.treatmentStatusId().value().toString(),
                aggregate.isActive(),
                aggregate.createdAt().toString(),
                aggregate.updatedAt().toString()
        );
    }
}