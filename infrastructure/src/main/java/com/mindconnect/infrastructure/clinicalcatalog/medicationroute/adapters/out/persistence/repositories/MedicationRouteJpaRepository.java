package com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.entity.MedicationRouteJpaEntity;

public interface MedicationRouteJpaRepository extends JpaRepository<MedicationRouteJpaEntity, UUID> {

    Optional<MedicationRouteJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}