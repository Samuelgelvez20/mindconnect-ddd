package com.mindconnect.domain.clinicalrecord.riskassessment.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RiskAssessmentUpdatedEvent(
        RiskAssessmentId id,
        EncounterId encounterId,
        RiskLevelId riskLevelId,
        boolean suicidalIdeation,
        boolean suicidePlan,
        boolean suicideIntent,
        boolean selfHarm,
        boolean harmToOthers,
        ProfessionalId assessedBy,
        Instant occurredOn
) implements DomainEvent {

    public RiskAssessmentUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(encounterId, "encounterId must not be null");
        Objects.requireNonNull(riskLevelId, "riskLevelId must not be null");
        Objects.requireNonNull(assessedBy, "assessedBy must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}