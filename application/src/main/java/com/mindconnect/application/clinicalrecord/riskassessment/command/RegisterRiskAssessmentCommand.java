package com.mindconnect.application.clinicalrecord.riskassessment.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RegisterRiskAssessmentCommand(
        EncounterId encounterId,
        RiskLevelId riskLevelId,
        boolean suicidalIdeation,
        boolean suicidePlan,
        boolean suicideIntent,
        boolean selfHarm,
        boolean harmToOthers,
        String protectiveFactors,
        String riskFactors,
        String clinicalActions,
        String observations,
        ProfessionalId assessedBy) {

    public RegisterRiskAssessmentCommand {
        Objects.requireNonNull(encounterId, "encounterId must not be null");
        Objects.requireNonNull(riskLevelId, "riskLevelId must not be null");
        Objects.requireNonNull(assessedBy, "assessedBy must not be null");
    }
}