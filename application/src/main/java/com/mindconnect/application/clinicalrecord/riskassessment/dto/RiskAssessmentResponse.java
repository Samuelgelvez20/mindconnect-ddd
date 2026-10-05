package com.mindconnect.application.clinicalrecord.riskassessment.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;

public record RiskAssessmentResponse(
        UUID id,
        UUID encounterId,
        UUID riskLevelId,
        boolean suicidalIdeation,
        boolean suicidePlan,
        boolean suicideIntent,
        boolean selfHarm,
        boolean harmToOthers,
        String protectiveFactors,
        String riskFactors,
        String clinicalActions,
        String observations,
        Instant assessedAt,
        UUID assessedBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static RiskAssessmentResponse from(RiskAssessment assessment) {
        return new RiskAssessmentResponse(
                assessment.id().value(),
                assessment.encounterId().value(),
                assessment.riskLevelId().value(),
                assessment.suicidalIdeation(),
                assessment.suicidePlan(),
                assessment.suicideIntent(),
                assessment.selfHarm(),
                assessment.harmToOthers(),
                assessment.protectiveFactors(),
                assessment.riskFactors(),
                assessment.clinicalActions(),
                assessment.observations(),
                assessment.assessedAt(),
                assessment.assessedBy().value(),
                assessment.createdAt(),
                assessment.updatedAt()
        );
    }
}