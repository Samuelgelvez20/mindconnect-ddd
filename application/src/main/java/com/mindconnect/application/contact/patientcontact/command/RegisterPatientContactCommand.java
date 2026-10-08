package com.mindconnect.application.contact.patientcontact.command;

import java.util.Objects;

import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;

public record RegisterPatientContactCommand(
        ContactId contactId,
        PatientId patientId,
        RelationshipTypeId relationshipTypeId) {

    public RegisterPatientContactCommand {
        Objects.requireNonNull(contactId, "contactId must not be null");
        Objects.requireNonNull(patientId, "patientId must not be null");
        Objects.requireNonNull(relationshipTypeId, "relationshipTypeId must not be null");
    }
}