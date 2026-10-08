package com.mindconnect.application.contact.patientcontact.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.contact.patientcontact.model.aggregate.PatientContact;

public record PatientContactResponse(
        UUID id,
        UUID contactId,
        UUID patientId,
        boolean isPrimaryContact,
        boolean isEmergencyContact,
        UUID relationshipTypeId,
        Instant createdAt,
        Instant updatedAt
) {

    public static PatientContactResponse from(PatientContact patientContact) {
        return new PatientContactResponse(
                patientContact.id().value(),
                patientContact.contactId().value(),
                patientContact.patientId().value(),
                patientContact.isPrimaryContact(),
                patientContact.isEmergencyContact(),
                patientContact.relationshipTypeId().value(),
                patientContact.createdAt(),
                patientContact.updatedAt()
        );
    }
}