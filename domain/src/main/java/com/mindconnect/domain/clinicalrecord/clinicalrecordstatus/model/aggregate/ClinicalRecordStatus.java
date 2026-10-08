package com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event.ClinicalRecordStatusDeletedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event.ClinicalRecordStatusRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event.ClinicalRecordStatusUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.exception.InvalidClinicalRecordStatusException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;

public class ClinicalRecordStatus extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final ClinicalRecordStatusId id;
    private String code;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private ClinicalRecordStatus(
            ClinicalRecordStatusId id,
            String code,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ClinicalRecordStatus register(String code, String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ClinicalRecordStatusId id = ClinicalRecordStatusId.generate();

        ClinicalRecordStatus status = new ClinicalRecordStatus(
                id,
                requiredText(code, "code", CODE_MAX_LENGTH),
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        status.recordEvent(new ClinicalRecordStatusRegisteredEvent(id, now));
        return status;
    }

    public static ClinicalRecordStatus restore(
            ClinicalRecordStatusId id,
            String code,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new ClinicalRecordStatus(id, code, name, createdAt, updatedAt);
    }

    public void update(String code, String name) {

        this.code = requiredText(code, "code", CODE_MAX_LENGTH);
        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ClinicalRecordStatusUpdatedEvent(this.id, this.code, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ClinicalRecordStatusDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ClinicalRecordStatusId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidClinicalRecordStatusException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidClinicalRecordStatusException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}