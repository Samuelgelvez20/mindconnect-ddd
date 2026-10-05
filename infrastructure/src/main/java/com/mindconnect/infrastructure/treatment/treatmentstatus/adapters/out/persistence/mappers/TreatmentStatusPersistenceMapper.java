package com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.mappers;

import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.entity.TreatmentStatusJpaEntity;

public class TreatmentStatusPersistenceMapper {

    public TreatmentStatusJpaEntity toJpa(TreatmentStatus domain) {
        if (domain == null) {
            return null;
        }

        TreatmentStatusJpaEntity jpa = new TreatmentStatusJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setCode(domain.code());
        jpa.setName(domain.name());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public TreatmentStatus toDomain(TreatmentStatusJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return TreatmentStatus.restore(
                new TreatmentStatusId(jpa.getId()),
                jpa.getCode(),
                jpa.getName(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}