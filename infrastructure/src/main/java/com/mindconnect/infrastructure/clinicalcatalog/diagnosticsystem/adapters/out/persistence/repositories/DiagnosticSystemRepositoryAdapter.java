package com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.mappers.DiagnosticSystemPersistenceMapper;

public class DiagnosticSystemRepositoryAdapter implements DiagnosticSystemRepository {

    private final DiagnosticSystemJpaRepository jpaRepository;
    private final DiagnosticSystemPersistenceMapper mapper;

    public DiagnosticSystemRepositoryAdapter(
            DiagnosticSystemJpaRepository jpaRepository,
            DiagnosticSystemPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem save(com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem diagnosticSystem) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(diagnosticSystem)));
    }

    @Override
    public Optional<com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem> findById(DiagnosticSystemId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem> findByCode(String code) {
        return jpaRepository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, DiagnosticSystemId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem diagnosticSystem) {
        jpaRepository.deleteById(diagnosticSystem.id().value());
    }
}