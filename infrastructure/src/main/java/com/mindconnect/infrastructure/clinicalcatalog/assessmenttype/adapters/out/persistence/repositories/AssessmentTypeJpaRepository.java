package com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.entity.AssessmentTypeJpaEntity;

public interface AssessmentTypeJpaRepository extends JpaRepository<AssessmentTypeJpaEntity, UUID> {

    Optional<AssessmentTypeJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}