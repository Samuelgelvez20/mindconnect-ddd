package com.mindconnect.application.clinicalrecord.mentalstatusexam.command;

import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RegisterMentalStatusExamCommand(
        EncounterId encounterId,
        String appearance,
        String behavior,
        String attitude,
        String consciousness,
        String orientation,
        String attention,
        String memory,
        String speech,
        String mood,
        String affect,
        String thoughtProcess,
        String thoughtContent,
        String perception,
        String judgment,
        String insight,
        String psychomotorActivity,
        String observations,
        ProfessionalId createdBy) {

    public RegisterMentalStatusExamCommand {
        Objects.requireNonNull(encounterId, "encounterId must not be null");
        Objects.requireNonNull(createdBy, "createdBy must not be null");
    }
}