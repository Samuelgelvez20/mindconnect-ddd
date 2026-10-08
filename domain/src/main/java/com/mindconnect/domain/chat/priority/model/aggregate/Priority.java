package com.mindconnect.domain.chat.priority.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.priority.event.PriorityDeletedEvent;
import com.mindconnect.domain.chat.priority.event.PriorityRegisteredEvent;
import com.mindconnect.domain.chat.priority.event.PriorityUpdatedEvent;
import com.mindconnect.domain.chat.priority.exception.InvalidPriorityException;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public class Priority extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;

    private final PriorityId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private Priority(
            PriorityId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Priority register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        PriorityId id = PriorityId.generate();

        Priority type = new Priority(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        type.recordEvent(new PriorityRegisteredEvent(id, now));
        return type;
    }

    public static Priority restore(
            PriorityId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new Priority(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new PriorityUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new PriorityDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public PriorityId id() {
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
            throw new InvalidPriorityException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidPriorityException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}