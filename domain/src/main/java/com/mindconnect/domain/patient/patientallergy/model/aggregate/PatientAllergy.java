package com.mindconnect.domain.patient.patientallergy.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyDeletedEvent;
import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyRegisteredEvent;
import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyUpdatedEvent;
import com.mindconnect.domain.patient.patientallergy.exception.InvalidPatientAllergyException;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;


public class PatientAllergy extends AggregateRoot {

    public static final int SUBSTANCE_MAX_LENGTH = 200;
    public static final int SEVERITY_MAX_LENGTH = 20;

    private final PatientAllergyId id;
    private PatientId patientId;
    private String substance;
    private String reaction;
    private String severity;
    private boolean isValid;
    private String resolutionNumber;
    private boolean active;
    private Instant recordedAt;
    private ProfessionalId recordedBy;
    private final Instant createdAt;
    private Instant updatedAt;

    private PatientAllergy(
            PatientAllergyId id,
            PatientId patientId,
            String substance,
            String reaction,
            String severity,
            boolean isValid,
            String resolutionNumber,
            boolean active,
            Instant recordedAt,
            ProfessionalId recordedBy,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.patientId = patientId;
        this.substance = substance;
        this.reaction = reaction;
        this.severity = severity;
        this.isValid = isValid;
        this.resolutionNumber = resolutionNumber;
        this.active = active;
        this.recordedAt = Objects.requireNonNull(recordedAt, "recordedAt must not be null");
        this.recordedBy = Objects.requireNonNull(recordedBy, "recordedBy must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static PatientAllergy register(
            PatientId patientId,
            String substance,
            String reaction,
            String severity,
            ProfessionalId recordedBy) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        PatientAllergyId id = PatientAllergyId.generate();

        // Validate required fields
        if (patientId == null) {
            throw new InvalidPatientAllergyException("patientId must not be null");
        }
        if (recordedBy == null) {
            throw new InvalidPatientAllergyException("recordedBy must not be null");
        }

        PatientAllergy patientAllergy = new PatientAllergy(
                id,
                patientId,
                requiredText(substance, "substance", SUBSTANCE_MAX_LENGTH),
                optionalText(reaction, "reaction", 0),
                optionalText(severity, "severity", SEVERITY_MAX_LENGTH),
                false,
                null,
                true,
                now,
                recordedBy,
                now,
                now);

        patientAllergy.recordEvent(new PatientAllergyRegisteredEvent(id, now));
        return patientAllergy;
    }

    public static PatientAllergy restore(
            PatientAllergyId id,
            PatientId patientId,
            String substance,
            String reaction,
            String severity,
            boolean isValid,
            String resolutionNumber,
            boolean active,
            Instant recordedAt,
            ProfessionalId recordedBy,
            Instant createdAt,
            Instant updatedAt) {

        return new PatientAllergy(id, patientId, substance, reaction, severity, false, null, active,
                recordedAt, recordedBy, createdAt, updatedAt);
    }

    public void update(
            String substance,
            String reaction,
            String severity,
            boolean active) {

        this.substance = requiredText(substance, "substance", SUBSTANCE_MAX_LENGTH);
        this.reaction = optionalText(reaction, "reaction", 0);
        this.severity = optionalText(severity, "severity", SEVERITY_MAX_LENGTH);
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new PatientAllergyUpdatedEvent(this.id, this.updatedAt));
    }

    public void delete() {
        recordEvent(new PatientAllergyDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public PatientAllergyId id() {
        return id;
    }

    public PatientId patientId() {
        return patientId;
    }

    public String substance() {
        return substance;
    }

    public String reaction() {
        return reaction;
    }

    public String severity() {
        return severity;
    }

    public boolean active() {
        return active;
    }

    public boolean isValid() {
        return isValid;
    }

    public String resolutionNumber() {
        return resolutionNumber;
    }

    public Instant recordedAt() {
        return recordedAt;
    }

    public ProfessionalId recordedBy() {
        return recordedBy;
    }



    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidPatientAllergyException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (maxLength > 0 && trimmed.length() > maxLength) {
            throw new InvalidPatientAllergyException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (maxLength > 0 && trimmed.length() > maxLength) {
            throw new InvalidPatientAllergyException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}