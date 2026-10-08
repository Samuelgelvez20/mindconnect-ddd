package com.mindconnect.domain.chat.sendertype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.sendertype.event.SenderTypeDeletedEvent;
import com.mindconnect.domain.chat.sendertype.event.SenderTypeRegisteredEvent;
import com.mindconnect.domain.chat.sendertype.event.SenderTypeUpdatedEvent;
import com.mindconnect.domain.chat.sendertype.exception.InvalidSenderTypeException;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;

public class SenderType extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;

    private final SenderTypeId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private SenderType(
            SenderTypeId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static SenderType register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        SenderTypeId id = SenderTypeId.generate();

        SenderType type = new SenderType(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        type.recordEvent(new SenderTypeRegisteredEvent(id, now));
        return type;
    }

    public static SenderType restore(
            SenderTypeId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new SenderType(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new SenderTypeUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new SenderTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public SenderTypeId id() {
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
            throw new InvalidSenderTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidSenderTypeException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}