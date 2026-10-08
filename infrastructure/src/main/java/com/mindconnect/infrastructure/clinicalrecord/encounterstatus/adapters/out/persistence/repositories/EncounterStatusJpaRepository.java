package com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.entity.EncounterStatusJpaEntity;

public interface EncounterStatusJpaRepository extends JpaRepository<EncounterStatusJpaEntity, UUID> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}