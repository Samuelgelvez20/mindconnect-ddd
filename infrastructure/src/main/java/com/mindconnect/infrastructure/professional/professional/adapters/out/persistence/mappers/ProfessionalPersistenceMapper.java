package com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.mappers;

import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;
import com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.entity.ProfessionalJpaEntity;

public class ProfessionalPersistenceMapper {

    public ProfessionalJpaEntity toJpa(Professional domain) {
        if (domain == null) {
            return null;
        }

        ProfessionalJpaEntity jpa = new ProfessionalJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setDocumentTypeId(domain.documentTypeId().value());
        jpa.setDocumentNumber(domain.documentNumber());
        jpa.setFirstName(domain.firstName());
        jpa.setLastName(domain.lastName());
        jpa.setProfessionalTypeId(domain.professionalTypeId().value());
        jpa.setLicenseNumber(domain.licenseNumber());
        jpa.setActive(domain.active());
        jpa.setCityId(domain.cityId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public Professional toDomain(ProfessionalJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return Professional.restore(
                new ProfessionalId(jpa.getId()),
                new DocumentTypeId(jpa.getDocumentTypeId()),
                jpa.getDocumentNumber(),
                jpa.getFirstName(),
                jpa.getLastName(),
                new ProfessionalTypeId(jpa.getProfessionalTypeId()),
                jpa.getLicenseNumber(),
                jpa.isActive(),
                new CityMunicipalityId(jpa.getCityId()),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}