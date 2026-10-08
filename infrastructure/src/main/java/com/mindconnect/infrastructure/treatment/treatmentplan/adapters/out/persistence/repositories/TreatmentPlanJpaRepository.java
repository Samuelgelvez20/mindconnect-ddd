package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.entity.TreatmentPlanJpaEntity;

public interface TreatmentPlanJpaRepository extends JpaRepository<TreatmentPlanJpaEntity, UUID> {

    List<TreatmentPlanJpaEntity> findByEncounterId(UUID encounterId);

    List<TreatmentPlanJpaEntity> findByProfessionalId(UUID professionalId);

    List<TreatmentPlanJpaEntity> findByTreatmentStatusId(UUID treatmentStatusId);
}