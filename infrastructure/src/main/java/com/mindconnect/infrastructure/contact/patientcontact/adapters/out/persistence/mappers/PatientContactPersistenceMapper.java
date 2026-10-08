package com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.mappers;

import java.time.Instant;

import com.mindconnect.domain.contact.patientcontact.model.aggregate.PatientContact;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;
import com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.entity.PatientContactJpaEntity;

public class PatientContactPersistenceMapper {

    public PatientContactJpaEntity toJpa(PatientContact domain) {
        if (domain == null) {
            return null;
        }

        PatientContactJpaEntity jpa = new PatientContactJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setContactId(domain.contactId().value());
        jpa.setPatientId(domain.patientId().value());
        jpa.setPrimaryContact(domain.isPrimaryContact());
        jpa.setEmergencyContact(domain.isEmergencyContact());
        jpa.setRelationshipTypeId(domain.relationshipTypeId().value());
        return jpa;
    }

    public PatientContact toDomain(PatientContactJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        Instant now = Instant.now(); // reconstruction values: V12 has no created_at/updated_at columns
        return PatientContact.restore(
                new PatientContactId(jpa.getId()),
                new ContactId(jpa.getContactId()),
                new PatientId(jpa.getPatientId()),
                jpa.isPrimaryContact(),
                jpa.isEmergencyContact(),
                new RelationshipTypeId(jpa.getRelationshipTypeId()),
                now,
                now
        );
    }
}