package com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.entity.EncounterTypeJpaEntity;

public interface EncounterTypeJpaRepository extends JpaRepository<EncounterTypeJpaEntity, UUID> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}