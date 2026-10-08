package com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.entity.EncounterJpaEntity;

public interface EncounterJpaRepository extends JpaRepository<EncounterJpaEntity, UUID> {

    List<EncounterJpaEntity> findByClinicalRecordId(UUID clinicalRecordId);
}