package com.mindconnect.application.treatment.treatmentgoal.dto;

public record TreatmentGoalResponse(
        String id,
        String treatmentPlanId,
        String description,
        String targetDate,
        String completedAt,
        String treatmentGoalStatusId,
        boolean active,
        String createdAt,
        String updatedAt
) {

    public static TreatmentGoalResponse from(com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal aggregate) {
        return new TreatmentGoalResponse(
                aggregate.id().value().toString(),
                aggregate.treatmentPlanId().value().toString(),
                aggregate.description(),
                aggregate.targetDate().toString(),
                aggregate.completedAt() != null ? aggregate.completedAt().toString() : null,
                aggregate.treatmentGoalStatusId().value().toString(),
                aggregate.isActive(),
                aggregate.createdAt().toString(),
                aggregate.updatedAt().toString()
        );
    }
}