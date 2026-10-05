package com.mindconnect.domain.professional.professionaltype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeDeletedEvent;
import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeRegisteredEvent;
import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeUpdatedEvent;
import com.mindconnect.domain.professional.professionaltype.exception.InvalidProfessionalTypeException;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public class ProfessionalType extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 40;

    private final ProfessionalTypeId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private ProfessionalType(
            ProfessionalTypeId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ProfessionalType register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ProfessionalTypeId id = ProfessionalTypeId.generate();

        ProfessionalType professionalType = new ProfessionalType(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        professionalType.recordEvent(new ProfessionalTypeRegisteredEvent(id, now));
        return professionalType;
    }

    public static ProfessionalType restore(
            ProfessionalTypeId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new ProfessionalType(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ProfessionalTypeUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ProfessionalTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ProfessionalTypeId id() {
        return id;
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
            throw new InvalidProfessionalTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidProfessionalTypeException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}