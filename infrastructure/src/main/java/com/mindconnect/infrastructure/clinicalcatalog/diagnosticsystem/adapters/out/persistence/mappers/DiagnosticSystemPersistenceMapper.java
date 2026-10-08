package com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.entity.DiagnosticSystemJpaEntity;

public class DiagnosticSystemPersistenceMapper {

    public DiagnosticSystemJpaEntity toJpa(com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem domain) {
        if (domain == null) {
            return null;
        }

        DiagnosticSystemJpaEntity jpa = new DiagnosticSystemJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setCode(domain.code());
        jpa.setName(domain.name());
        jpa.setVersion(domain.version());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem toDomain(DiagnosticSystemJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem.restore(
                new com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId(jpa.getId()),
                jpa.getCode(),
                jpa.getName(),
                jpa.getVersion(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}