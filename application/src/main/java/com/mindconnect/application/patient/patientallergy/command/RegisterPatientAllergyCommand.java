package com.mindconnect.application.patient.patientallergy.command;

import java.util.Objects;

import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RegisterPatientAllergyCommand(
        PatientId patientId,
        String substance,
        String reaction,
        String severity,
        ProfessionalId recordedBy) {

    public RegisterPatientAllergyCommand {
        Objects.requireNonNull(patientId, "patientId must not be null");
        Objects.requireNonNull(substance, "substance must not be null");
        Objects.requireNonNull(recordedBy, "recordedBy must not be null");
    }
}