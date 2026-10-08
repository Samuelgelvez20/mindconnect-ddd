package com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.mappers;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.entity.StateRegionJpaEntity;

public class StateRegionPersistenceMapper {

    public StateRegionJpaEntity toJpa(StateRegion domain) {
        if (domain == null) {
            return null;
        }

        StateRegionJpaEntity jpa = new StateRegionJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCode(domain.code());
        jpa.setDescription(domain.description());
        jpa.setActive(domain.active());
        jpa.setCountryId(domain.countryId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public StateRegion toDomain(StateRegionJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return StateRegion.restore(
                new StateRegionId(jpa.getId()),
                jpa.getName(),
                jpa.getCode(),
                jpa.getDescription(),
                new CountryId(jpa.getCountryId()),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}