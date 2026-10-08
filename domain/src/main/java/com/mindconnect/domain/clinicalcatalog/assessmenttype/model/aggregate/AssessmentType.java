package com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.event.AssessmentTypeDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.event.AssessmentTypeRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.event.AssessmentTypeUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.exception.InvalidAssessmentTypeException;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;

public class AssessmentType extends AggregateRoot {

    public static final int CODE_MAX_LENGTH = 20;
    public static final int NAME_MAX_LENGTH = 50;

    private final AssessmentTypeId id;
    private String code;
    private String name;
    private String description;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private AssessmentType(
            AssessmentTypeId id,
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

    public static AssessmentType register(String code, String name, String description) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        AssessmentTypeId id = AssessmentTypeId.generate();

        String requiredCode = requiredText(code, "code", CODE_MAX_LENGTH);
        String requiredName = requiredText(name, "name", NAME_MAX_LENGTH);
        String normalizedDescription = normalizeDescription(description);

        AssessmentType type = new AssessmentType(
                id,
                requiredCode,
                requiredName,
                normalizedDescription,
                true,
                now,
                now);

        type.recordEvent(new AssessmentTypeRegisteredEvent(id, now));
        return type;
    }

    public static AssessmentType restore(
            AssessmentTypeId id,
            String code,
            String name,
            String description,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new AssessmentType(id, code, name, description, active, createdAt, updatedAt);
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

        recordEvent(new AssessmentTypeUpdatedEvent(this.id, this.code, this.name, this.description, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new AssessmentTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public AssessmentTypeId id() {
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
            throw new InvalidAssessmentTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidAssessmentTypeException(field + " must have at most " + maxLength + " characters");
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