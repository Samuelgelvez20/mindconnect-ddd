package com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record UpdateRiskAssessmentRequest(

        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotNull(message = "riskLevelId is required")
        UUID riskLevelId,

        @NotNull(message = "assessedBy is required")
        UUID assessedBy,

        Instant assessedAt,

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