package com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.entity.ConsentTypeJpaEntity;

public class ConsentTypePersistenceMapper {

    public ConsentTypeJpaEntity toJpa(com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType domain) {
        if (domain == null) {
            return null;
        }

        ConsentTypeJpaEntity jpa = new ConsentTypeJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setCode(domain.code());
        jpa.setName(domain.name());
        jpa.setDescription(domain.description());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType toDomain(ConsentTypeJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType.restore(
                new com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId(jpa.getId()),
                jpa.getCode(),
                jpa.getName(),
                jpa.getDescription(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}