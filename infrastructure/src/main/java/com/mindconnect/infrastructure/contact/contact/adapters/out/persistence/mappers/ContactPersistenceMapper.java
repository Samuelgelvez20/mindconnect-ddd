package com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.mappers;

import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.infrastructure.contact.contact.adapters.out.persistence.entity.ContactJpaEntity;

public class ContactPersistenceMapper {

    public ContactJpaEntity toJpa(Contact domain) {
        if (domain == null) {
            return null;
        }

        ContactJpaEntity jpa = new ContactJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setFullName(domain.fullName());
        jpa.setEmail(domain.email());
        jpa.setNotes(domain.notes());
        jpa.setCityId(domain.cityId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setCreatedBy(domain.createdBy().value());
        jpa.setUpdatedAt(domain.updatedAt());
        jpa.setUpdatedBy(domain.updatedBy() != null ? domain.updatedBy().value() : null);
        return jpa;
    }

    public Contact toDomain(ContactJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return Contact.restore(
                new ContactId(jpa.getId()),
                jpa.getFullName(),
                jpa.getEmail(),
                jpa.getNotes(),
                new CityMunicipalityId(jpa.getCityId()),
                new ProfessionalId(jpa.getCreatedBy()),
                jpa.getUpdatedBy() != null ? new ProfessionalId(jpa.getUpdatedBy()) : null,
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}