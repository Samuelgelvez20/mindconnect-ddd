package com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.mappers;

import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.entity.CityMunicipalityJpaEntity;

public class CityMunicipalityPersistenceMapper {

    public CityMunicipalityJpaEntity toJpa(CityMunicipality domain) {
        if (domain == null) {
            return null;
        }

        CityMunicipalityJpaEntity jpa = new CityMunicipalityJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCode(domain.code());
        jpa.setDescription(domain.description());
        jpa.setActive(domain.active());
        jpa.setRegionId(domain.regionId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public CityMunicipality toDomain(CityMunicipalityJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return CityMunicipality.restore(
                new CityMunicipalityId(jpa.getId()),
                jpa.getName(),
                jpa.getCode(),
                jpa.getDescription(),
                new StateRegionId(jpa.getRegionId()),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}