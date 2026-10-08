package com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.mappers;

import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.entity.CountryJpaEntity;

public class CountryPersistenceMapper {

    public CountryJpaEntity toJpa(Country domain) {
        if (domain == null) {
            return null;
        }

        CountryJpaEntity jpa = new CountryJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCode(domain.code());
        jpa.setDescription(domain.description());
        jpa.setActive(domain.active());
        jpa.setTelephonePrefix(domain.telephonePrefix());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public Country toDomain(CountryJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return Country.restore(
                new CountryId(jpa.getId()),
                jpa.getName(),
                jpa.getCode(),
                jpa.getDescription(),
                jpa.getTelephonePrefix(),
                jpa.isActive(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}
