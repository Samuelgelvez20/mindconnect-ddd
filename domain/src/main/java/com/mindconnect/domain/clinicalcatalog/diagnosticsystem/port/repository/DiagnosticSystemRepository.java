package com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;

public interface DiagnosticSystemRepository {

    DiagnosticSystem save(DiagnosticSystem diagnosticSystem);

    Optional<DiagnosticSystem> findById(DiagnosticSystemId id);

    Optional<DiagnosticSystem> findByCode(String code);

    boolean existsByCodeAndIdNot(String code, DiagnosticSystemId id);

    List<DiagnosticSystem> findAll();

    void delete(DiagnosticSystem diagnosticSystem);
}