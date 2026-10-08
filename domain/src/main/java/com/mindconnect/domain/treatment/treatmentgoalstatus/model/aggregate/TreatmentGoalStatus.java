package com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.treatment.treatmentgoalstatus.event.TreatmentGoalStatusDeletedEvent;
import com.mindconnect.domain.treatment.treatmentgoalstatus.event.TreatmentGoalStatusRegisteredEvent;
import com.mindconnect.domain.treatment.treatmentgoalstatus.event.TreatmentGoalStatusUpdatedEvent;
import com.mindconnect.domain.treatment.treatmentgoalstatus.exception.InvalidTreatmentGoalStatusException;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;

public class TreatmentGoalStatus extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final TreatmentGoalStatusId id;
    private String code;
    private String name;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private TreatmentGoalStatus(
            TreatmentGoalStatusId id,
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

    public static TreatmentGoalStatus register(String code, String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        TreatmentGoalStatusId id = TreatmentGoalStatusId.generate();

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);

        TreatmentGoalStatus status = new TreatmentGoalStatus(
                id,
                requiredCode,
                requiredName,
                true,
                now,
                now);

        status.recordEvent(new TreatmentGoalStatusRegisteredEvent(id, now));
        return status;
    }

    public static TreatmentGoalStatus restore(
            TreatmentGoalStatusId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new TreatmentGoalStatus(id, code, name, active, createdAt, updatedAt);
    }

    public void update(String code, String name) {

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);

        this.code = requiredCode;
        this.name = requiredName;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new TreatmentGoalStatusUpdatedEvent(this.id, this.code, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new TreatmentGoalStatusDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public TreatmentGoalStatusId id() {
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
            throw new InvalidTreatmentGoalStatusException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidTreatmentGoalStatusException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}