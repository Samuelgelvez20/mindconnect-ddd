package com.mindconnect.application.contact.patientcontact.command;

import java.util.Objects;

import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public record UpdatePatientContactCommand(
        PatientContactId id,
        boolean isPrimaryContact,
        boolean isEmergencyContact,
        RelationshipTypeId relationshipTypeId) {

    public UpdatePatientContactCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(relationshipTypeId, "relationshipTypeId must not be null");
    }
}