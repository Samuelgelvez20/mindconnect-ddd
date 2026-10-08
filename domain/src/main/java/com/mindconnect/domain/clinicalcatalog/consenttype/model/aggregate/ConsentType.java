package com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalcatalog.consenttype.event.ConsentTypeDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.event.ConsentTypeRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.event.ConsentTypeUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.exception.InvalidConsentTypeException;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;

public class ConsentType extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final ConsentTypeId id;
    private String code;
    private String name;
    private String description;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private ConsentType(
            ConsentTypeId id,
            String code,
            String name,
            String description,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.code = code;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ConsentType register(String code, String name, String description) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ConsentTypeId id = ConsentTypeId.generate();

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);
        String normalizedDescription = normalizeDescription(description);

        ConsentType type = new ConsentType(
                id,
                requiredCode,
                requiredName,
                normalizedDescription,
                true,
                now,
                now);

        type.recordEvent(new ConsentTypeRegisteredEvent(id, now));
        return type;
    }

    public static ConsentType restore(
            ConsentTypeId id,
            String code,
            String name,
            String description,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new ConsentType(id, code, name, description, active, createdAt, updatedAt);
    }

    public void update(String code, String name, String description, boolean active) {

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);
        String normalizedDescription = normalizeDescription(description);

        this.code = requiredCode;
        this.name = requiredName;
        this.description = normalizedDescription;
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ConsentTypeUpdatedEvent(this.id, this.code, this.name, this.description, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ConsentTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ConsentTypeId id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
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

    void setActive(boolean active) {
        this.active = active;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidConsentTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidConsentTypeException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String normalizeDescription(String description) {
        if (description == null) {
            return null;
        }
        String trimmed = description.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}