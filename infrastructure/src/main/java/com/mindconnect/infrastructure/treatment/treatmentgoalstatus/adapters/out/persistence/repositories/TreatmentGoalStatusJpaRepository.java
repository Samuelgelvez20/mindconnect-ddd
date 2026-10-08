package com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.entity.TreatmentGoalStatusJpaEntity;

public interface TreatmentGoalStatusJpaRepository extends JpaRepository<TreatmentGoalStatusJpaEntity, UUID> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}