package com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.clinicalnote.event.ClinicalNoteDeletedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.event.ClinicalNoteRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.event.ClinicalNoteUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.exception.InvalidClinicalNoteException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class ClinicalNote extends AggregateRoot {

    private final ClinicalNoteId id;
    private EncounterId encounterId;
    private ProfessionalId professionalId;
    private String subjective;
    private String objective;
    private String assessment;
    private String plan;
    private String additionalNotes;
    private Instant signedAt;
    private final Instant createdAt;
    private Instant updatedAt;

    private ClinicalNote(
            ClinicalNoteId id,
            EncounterId encounterId,
            ProfessionalId professionalId,
            String subjective,
            String objective,
            String assessment,
            String plan,
            String additionalNotes,
            Instant signedAt,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.encounterId = encounterId;
        this.professionalId = professionalId;
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.plan = plan;
        this.additionalNotes = additionalNotes;
        this.signedAt = signedAt;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ClinicalNote register(
            EncounterId encounterId,
            ProfessionalId professionalId,
            String subjective,
            String objective,
            String assessment,
            String plan,
            String additionalNotes) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ClinicalNoteId id = ClinicalNoteId.generate();

        if (encounterId == null) {
            throw new InvalidClinicalNoteException("encounterId must not be null");
        }
        if (professionalId == null) {
            throw new InvalidClinicalNoteException("professionalId must not be null");
        }

        ClinicalNote note = new ClinicalNote(
                id,
                encounterId,
                professionalId,
                optionalText(subjective, "subjective"),
                optionalText(objective, "objective"),
                optionalText(assessment, "assessment"),
                optionalText(plan, "plan"),
                optionalText(additionalNotes, "additionalNotes"),
                null,
                now,
                now);

        note.recordEvent(new ClinicalNoteRegisteredEvent(id, now));
        return note;
    }

    public static ClinicalNote restore(
            ClinicalNoteId id,
            EncounterId encounterId,
            ProfessionalId professionalId,
            String subjective,
            String objective,
            String assessment,
            String plan,
            String additionalNotes,
            Instant signedAt,
            Instant createdAt,
            Instant updatedAt) {

        return new ClinicalNote(id, encounterId, professionalId, subjective, objective, assessment,
                plan, additionalNotes, signedAt, createdAt, updatedAt);
    }

    public void update(
            EncounterId encounterId,
            ProfessionalId professionalId,
            String subjective,
            String objective,
            String assessment,
            String plan,
            String additionalNotes,
            Instant signedAt) {

        this.encounterId = Objects.requireNonNull(encounterId, "encounterId must not be null");
        this.professionalId = Objects.requireNonNull(professionalId, "professionalId must not be null");
        this.subjective = optionalText(subjective, "subjective");
        this.objective = optionalText(objective, "objective");
        this.assessment = optionalText(assessment, "assessment");
        this.plan = optionalText(plan, "plan");
        this.additionalNotes = optionalText(additionalNotes, "additionalNotes");
        this.signedAt = signedAt;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ClinicalNoteUpdatedEvent(this.id, this.encounterId, this.professionalId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ClinicalNoteDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ClinicalNoteId id() {
        return id;
    }

    public EncounterId encounterId() {
        return encounterId;
    }

    public ProfessionalId professionalId() {
        return professionalId;
    }

    public String subjective() {
        return subjective;
    }

    public String objective() {
        return objective;
    }

    public String assessment() {
        return assessment;
    }

    public String plan() {
        return plan;
    }

    public String additionalNotes() {
        return additionalNotes;
    }

    public Instant signedAt() {
        return signedAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String optionalText(String value, String field) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}