package com.mindconnect.application.patient.patientallergy.command;

import java.util.Objects;

import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;

public record UpdatePatientAllergyCommand(
        PatientAllergyId id,
        String substance,
        String reaction,
        String severity,
        boolean active) {

    public UpdatePatientAllergyCommand {
        Objects.requireNonNull(id, "id must not be null");
    }
}