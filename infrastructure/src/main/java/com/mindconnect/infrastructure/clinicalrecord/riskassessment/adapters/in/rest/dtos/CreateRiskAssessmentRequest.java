package com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateRiskAssessmentRequest(

        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotNull(message = "riskLevelId is required")
        UUID riskLevelId,

        @NotNull(message = "assessedBy is required")
        UUID assessedBy,

        Boolean suicidalIdeation,
        Boolean suicidePlan,
        Boolean suicideIntent,
        Boolean selfHarm,
        Boolean harmToOthers,

        String protectiveFactors,
        String riskFactors,
        String clinicalActions,
        String observations
) {
}