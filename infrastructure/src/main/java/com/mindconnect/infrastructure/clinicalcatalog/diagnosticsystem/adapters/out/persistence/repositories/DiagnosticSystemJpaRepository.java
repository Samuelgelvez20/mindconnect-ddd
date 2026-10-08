package com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.entity.DiagnosticSystemJpaEntity;

public interface DiagnosticSystemJpaRepository extends JpaRepository<DiagnosticSystemJpaEntity, UUID> {

    Optional<DiagnosticSystemJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}