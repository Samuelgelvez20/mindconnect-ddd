package com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.mappers;

import java.time.Instant;

import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.entity.RiskAssessmentJpaEntity;

public class RiskAssessmentPersistenceMapper {

    public RiskAssessmentJpaEntity toJpa(com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment domain) {
        if (domain == null) {
            return null;
        }

        RiskAssessmentJpaEntity jpa = new RiskAssessmentJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setEncounterId(domain.encounterId().value());
        jpa.setRiskLevelId(domain.riskLevelId().value());
        jpa.setSuicidalIdeation(domain.suicidalIdeation());
        jpa.setSuicidePlan(domain.suicidePlan());
        jpa.setSuicideIntent(domain.suicideIntent());
        jpa.setSelfHarm(domain.selfHarm());
        jpa.setHarmToOthers(domain.harmToOthers());
        jpa.setProtectiveFactors(domain.protectiveFactors());
        jpa.setRiskFactors(domain.riskFactors());
        jpa.setClinicalActions(domain.clinicalActions());
        jpa.setObservations(domain.observations());
        jpa.setAssessedAt(domain.assessedAt());
        jpa.setAssessedBy(domain.assessedBy().value());
        return jpa;
    }

    public com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment toDomain(RiskAssessmentJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        Instant now = Instant.now(); // reconstruction values: V26 has no created_at/updated_at columns
        return com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment.restore(
                new com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId(jpa.getId()),
                new com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId(jpa.getEncounterId()),
                new com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId(jpa.getRiskLevelId()),
                jpa.isSuicidalIdeation(),
                jpa.isSuicidePlan(),
                jpa.isSuicideIntent(),
                jpa.isSelfHarm(),
                jpa.isHarmToOthers(),
                jpa.getProtectiveFactors(),
                jpa.getRiskFactors(),
                jpa.getClinicalActions(),
                jpa.getObservations(),
                jpa.getAssessedAt(),
                new com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId(jpa.getAssessedBy()),
                now,
                now
        );
    }
}