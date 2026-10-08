package com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.entity.TreatmentGoalJpaEntity;

public interface TreatmentGoalJpaRepository extends JpaRepository<TreatmentGoalJpaEntity, UUID> {

    List<TreatmentGoalJpaEntity> findByTreatmentPlanId(UUID treatmentPlanId);
}