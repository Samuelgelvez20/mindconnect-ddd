package com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.encountermodality.event.EncounterModalityDeletedEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.event.EncounterModalityRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.event.EncounterModalityUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.exception.InvalidEncounterModalityException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;

public class EncounterModality extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final EncounterModalityId id;
    private String code;
    private String name;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private EncounterModality(
            EncounterModalityId id,
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

    public static EncounterModality register(String code, String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        EncounterModalityId id = EncounterModalityId.generate();

        EncounterModality modality = new EncounterModality(
                id,
                requiredText(code, "code", CODE_MAX_LENGTH),
                requiredText(name, "name", NAME_MAX_LENGTH),
                true,
                now,
                now);

        modality.recordEvent(new EncounterModalityRegisteredEvent(id, now));
        return modality;
    }

    public static EncounterModality restore(
            EncounterModalityId id,
            String code,
            String name,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new EncounterModality(id, code, name, active, createdAt, updatedAt);
    }

    public void update(String code, String name, boolean active) {

        this.code = requiredText(code, "code", CODE_MAX_LENGTH);
        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new EncounterModalityUpdatedEvent(this.id, this.code, this.name, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new EncounterModalityDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public EncounterModalityId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidEncounterModalityException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidEncounterModalityException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}