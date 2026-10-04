package com.mindconnect.domain.referencedata.gender.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.referencedata.gender.event.GenderDeletedEvent;
import com.mindconnect.domain.referencedata.gender.event.GenderRegisteredEvent;
import com.mindconnect.domain.referencedata.gender.event.GenderUpdatedEvent;
import com.mindconnect.domain.referencedata.gender.exception.InvalidGenderException;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public class Gender extends AggregateRoot {

    public static final int DESCRIPTION_MAX_LENGTH = 50;

    private final GenderId id;
    private String description;
    private final Instant createdAt;
    private Instant updatedAt;

    private Gender(
            GenderId id,
            String description,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.description = description;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Gender register(String description) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        GenderId id = GenderId.generate();

        Gender gender = new Gender(
                id,
                requiredText(description, "description", DESCRIPTION_MAX_LENGTH),
                now,
                now);

        gender.recordEvent(new GenderRegisteredEvent(id, now));
        return gender;
    }

    public static Gender restore(
            GenderId id,
            String description,
            Instant createdAt,
            Instant updatedAt) {

        return new Gender(id, description, createdAt, updatedAt);
    }

    public void update(String description) {

        this.description = requiredText(description, "description", DESCRIPTION_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new GenderUpdatedEvent(this.id, this.description, this.updatedAt));
    }

    public void delete() {
        recordEvent(new GenderDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public GenderId id() {
        return id;
    }

    public String description() {
        return description;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidGenderException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidGenderException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}