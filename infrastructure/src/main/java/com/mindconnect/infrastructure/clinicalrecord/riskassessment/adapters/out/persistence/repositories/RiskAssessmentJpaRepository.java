package com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.entity.RiskAssessmentJpaEntity;

public interface RiskAssessmentJpaRepository extends JpaRepository<RiskAssessmentJpaEntity, UUID> {

    List<RiskAssessmentJpaEntity> findByEncounterId(UUID encounterId);

    boolean existsByEncounterId(UUID encounterId);
}