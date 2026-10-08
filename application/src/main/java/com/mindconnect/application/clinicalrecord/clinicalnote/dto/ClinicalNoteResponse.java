package com.mindconnect.application.clinicalrecord.clinicalnote.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;

public record ClinicalNoteResponse(
        UUID id,
        UUID encounterId,
        UUID professionalId,
        String subjective,
        String objective,
        String assessment,
        String plan,
        String additionalNotes,
        Instant signedAt,
        Instant createdAt,
        Instant updatedAt
) {

    public static ClinicalNoteResponse from(ClinicalNote note) {
        return new ClinicalNoteResponse(
                note.id().value(),
                note.encounterId().value(),
                note.professionalId().value(),
                note.subjective(),
                note.objective(),
                note.assessment(),
                note.plan(),
                note.additionalNotes(),
                note.signedAt(),
                note.createdAt(),
                note.updatedAt()
        );
    }
}