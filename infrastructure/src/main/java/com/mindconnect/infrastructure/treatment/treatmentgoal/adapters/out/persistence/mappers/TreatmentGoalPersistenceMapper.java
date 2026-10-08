package com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.mappers;

import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.entity.TreatmentGoalJpaEntity;

public class TreatmentGoalPersistenceMapper {

    public TreatmentGoalJpaEntity toJpa(TreatmentGoal domain) {
        if (domain == null) {
            return null;
        }

        TreatmentGoalJpaEntity jpa = new TreatmentGoalJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setTreatmentPlanId(domain.treatmentPlanId().value());
        jpa.setDescription(domain.description());
        jpa.setTargetDate(domain.targetDate());
        jpa.setCompletedAt(domain.completedAt());
        jpa.setTreatmentGoalStatusId(domain.treatmentGoalStatusId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public TreatmentGoal toDomain(TreatmentGoalJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return TreatmentGoal.restore(
                new TreatmentGoalId(jpa.getId()),
                new TreatmentPlanId(jpa.getTreatmentPlanId()),
                jpa.getDescription(),
                jpa.getTargetDate(),
                jpa.getCompletedAt(),
                new TreatmentGoalStatusId(jpa.getTreatmentGoalStatusId()),
                true,
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}