package com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.entity.MedicationRouteJpaEntity;

public class MedicationRoutePersistenceMapper {

    public MedicationRouteJpaEntity toJpa(com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute domain) {
        if (domain == null) {
            return null;
        }

        MedicationRouteJpaEntity jpa = new MedicationRouteJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setCode(domain.code());
        jpa.setName(domain.name());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute toDomain(MedicationRouteJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute.restore(
                new com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId(jpa.getId()),
                jpa.getCode(),
                jpa.getName(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}