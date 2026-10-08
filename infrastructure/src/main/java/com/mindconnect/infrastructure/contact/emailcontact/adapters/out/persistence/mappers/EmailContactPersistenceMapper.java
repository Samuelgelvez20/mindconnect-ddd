package com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.mappers;

import com.mindconnect.domain.contact.emailcontact.model.aggregate.EmailContact;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.entity.EmailContactJpaEntity;

public class EmailContactPersistenceMapper {

    public EmailContactJpaEntity toJpa(EmailContact domain) {
        if (domain == null) {
            return null;
        }

        EmailContactJpaEntity jpa = new EmailContactJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setContactId(domain.contactId().value());
        jpa.setEmail(domain.email());
        jpa.setNotes(domain.notes());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public EmailContact toDomain(EmailContactJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return EmailContact.restore(
                new EmailContactId(jpa.getId()),
                new ContactId(jpa.getContactId()),
                jpa.getEmail(),
                jpa.getNotes(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}