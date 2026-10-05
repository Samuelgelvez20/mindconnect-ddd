package com.mindconnect.application.clinicalrecord.clinicalnote.command;

import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RegisterClinicalNoteCommand(
        EncounterId encounterId,
        ProfessionalId professionalId,
        String subjective,
        String objective,
        String assessment,
        String plan,
        String additionalNotes) {

    public RegisterClinicalNoteCommand {
        Objects.requireNonNull(encounterId, "encounterId must not be null");
        Objects.requireNonNull(professionalId, "professionalId must not be null");
    }
}