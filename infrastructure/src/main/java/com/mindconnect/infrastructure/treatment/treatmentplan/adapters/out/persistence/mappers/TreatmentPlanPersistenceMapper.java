package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.mappers;

import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.entity.TreatmentPlanJpaEntity;

public class TreatmentPlanPersistenceMapper {

    public TreatmentPlanJpaEntity toJpa(TreatmentPlan domain) {
        if (domain == null) {
            return null;
        }

        TreatmentPlanJpaEntity jpa = new TreatmentPlanJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setEncounterId(domain.encounterId().value());
        jpa.setProfessionalId(domain.professionalId().value());
        jpa.setTitle(domain.title());
        jpa.setDescription(domain.description());
        jpa.setStartDate(domain.startDate());
        jpa.setEndDate(domain.endDate());
        jpa.setTreatmentStatusId(domain.treatmentStatusId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public TreatmentPlan toDomain(TreatmentPlanJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return TreatmentPlan.restore(
                new TreatmentPlanId(jpa.getId()),
                new EncounterId(jpa.getEncounterId()),
                new ProfessionalId(jpa.getProfessionalId()),
                jpa.getTitle(),
                jpa.getDescription(),
                jpa.getStartDate(),
                jpa.getEndDate(),
                new TreatmentStatusId(jpa.getTreatmentStatusId()),
                true,
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}