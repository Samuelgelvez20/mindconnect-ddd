package com.mindconnect.domain.treatment.treatmentstatus.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.treatment.treatmentstatus.event.TreatmentStatusDeletedEvent;
import com.mindconnect.domain.treatment.treatmentstatus.event.TreatmentStatusRegisteredEvent;
import com.mindconnect.domain.treatment.treatmentstatus.event.TreatmentStatusUpdatedEvent;
import com.mindconnect.domain.treatment.treatmentstatus.exception.InvalidTreatmentStatusException;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;

public class TreatmentStatus extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final TreatmentStatusId id;
    private String code;
    private String name;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private TreatmentStatus(
            TreatmentStatusId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static TreatmentStatus register(String code, String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        TreatmentStatusId id = TreatmentStatusId.generate();

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);

        TreatmentStatus status = new TreatmentStatus(
                id,
                requiredCode,
                requiredName,
                true,
                now,
                now);

        status.recordEvent(new TreatmentStatusRegisteredEvent(id, now));
        return status;
    }

    public static TreatmentStatus restore(
            TreatmentStatusId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new TreatmentStatus(id, code, name, active, createdAt, updatedAt);
    }

    public void update(String code, String name) {

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);

        this.code = requiredCode;
        this.name = requiredName;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new TreatmentStatusUpdatedEvent(this.id, this.code, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new TreatmentStatusDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public TreatmentStatusId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidTreatmentStatusException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidTreatmentStatusException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}