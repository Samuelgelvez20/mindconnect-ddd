package com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.mappers;

import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.entity.GenderJpaEntity;

public class GenderPersistenceMapper {

    public GenderJpaEntity toJpa(Gender domain) {
        if (domain == null) {
            return null;
        }

        GenderJpaEntity jpa = new GenderJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setDescription(domain.description());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public Gender toDomain(GenderJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return Gender.restore(
                new GenderId(jpa.getId()),
                jpa.getDescription(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}