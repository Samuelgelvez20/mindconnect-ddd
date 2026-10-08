package com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.entity.AssessmentTypeJpaEntity;

public class AssessmentTypePersistenceMapper {

    public AssessmentTypeJpaEntity toJpa(com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType domain) {
        if (domain == null) {
            return null;
        }

        AssessmentTypeJpaEntity jpa = new AssessmentTypeJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setCode(domain.code());
        jpa.setName(domain.name());
        jpa.setDescription(domain.description());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType toDomain(AssessmentTypeJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType.restore(
                new com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId(jpa.getId()),
                jpa.getCode(),
                jpa.getName(),
                jpa.getDescription(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}