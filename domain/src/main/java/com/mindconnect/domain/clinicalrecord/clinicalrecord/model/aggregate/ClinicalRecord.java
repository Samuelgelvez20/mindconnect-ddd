package com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.event.ClinicalRecordDeletedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.event.ClinicalRecordRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.event.ClinicalRecordUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.exception.InvalidClinicalRecordException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class ClinicalRecord extends AggregateRoot {

    public static final int RECORD_NUMBER_MAX_LENGTH = 50;

    private final ClinicalRecordId id;
    private PatientId patientId;
    private Instant creationDate;
    private String recordNumber;
    private Instant openedAt;
    private Instant closedAt;
    private ClinicalRecordStatusId statusId;
    private ProfessionalId createdBy;
    private final Instant createdAt;
    private Instant updatedAt;

    private ClinicalRecord(
            ClinicalRecordId id,
            PatientId patientId,
            Instant creationDate,
            String recordNumber,
            Instant openedAt,
            Instant closedAt,
            ClinicalRecordStatusId statusId,
            ProfessionalId createdBy,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.patientId = patientId;
        this.creationDate = creationDate;
        this.recordNumber = recordNumber;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.statusId = statusId;
        this.createdBy = createdBy;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ClinicalRecord register(
            PatientId patientId,
            String recordNumber,
            ClinicalRecordStatusId statusId,
            ProfessionalId createdBy) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ClinicalRecordId id = ClinicalRecordId.generate();

        if (patientId == null) {
            throw new InvalidClinicalRecordException("patientId must not be null");
        }
        if (statusId == null) {
            throw new InvalidClinicalRecordException("statusId must not be null");
        }
        if (createdBy == null) {
            throw new InvalidClinicalRecordException("createdBy must not be null");
        }

        ClinicalRecord record = new ClinicalRecord(
                id,
                patientId,
                now,
                requiredText(recordNumber, "recordNumber", RECORD_NUMBER_MAX_LENGTH),
                null,
                null,
                statusId,
                createdBy,
                now,
                now);

        record.recordEvent(new ClinicalRecordRegisteredEvent(id, now));
        return record;
    }

    public static ClinicalRecord restore(
            ClinicalRecordId id,
            PatientId patientId,
            Instant creationDate,
            String recordNumber,
            Instant openedAt,
            Instant closedAt,
            ClinicalRecordStatusId statusId,
            ProfessionalId createdBy,
            Instant createdAt,
            Instant updatedAt) {

        return new ClinicalRecord(id, patientId, creationDate, recordNumber, openedAt, closedAt, statusId, createdBy, createdAt, updatedAt);
    }

    public void update(
            PatientId patientId,
            String recordNumber,
            Instant openedAt,
            Instant closedAt,
            ClinicalRecordStatusId statusId) {

        this.patientId = Objects.requireNonNull(patientId, "patientId must not be null");
        this.recordNumber = requiredText(recordNumber, "recordNumber", RECORD_NUMBER_MAX_LENGTH);
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.statusId = Objects.requireNonNull(statusId, "statusId must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ClinicalRecordUpdatedEvent(this.id, this.patientId, this.recordNumber, this.statusId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ClinicalRecordDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ClinicalRecordId id() {
        return id;
    }

    public PatientId patientId() {
        return patientId;
    }

    public Instant creationDate() {
        return creationDate;
    }

    public String recordNumber() {
        return recordNumber;
    }

    public Instant openedAt() {
        return openedAt;
    }

    public Instant closedAt() {
        return closedAt;
    }

    public ClinicalRecordStatusId statusId() {
        return statusId;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidClinicalRecordException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidClinicalRecordException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}