package com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.entity.TreatmentStatusJpaEntity;

public interface TreatmentStatusJpaRepository extends JpaRepository<TreatmentStatusJpaEntity, UUID> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}