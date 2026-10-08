package com.mindconnect.domain.chat.messagetype.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.messagetype.event.MessageTypeDeletedEvent;
import com.mindconnect.domain.chat.messagetype.event.MessageTypeRegisteredEvent;
import com.mindconnect.domain.chat.messagetype.event.MessageTypeUpdatedEvent;
import com.mindconnect.domain.chat.messagetype.exception.InvalidMessageTypeException;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;

public class MessageType extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 100;

    private final MessageTypeId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private MessageType(
            MessageTypeId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static MessageType register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        MessageTypeId id = MessageTypeId.generate();

        MessageType type = new MessageType(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        type.recordEvent(new MessageTypeRegisteredEvent(id, now));
        return type;
    }

    public static MessageType restore(
            MessageTypeId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new MessageType(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new MessageTypeUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new MessageTypeDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public MessageTypeId id() {
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
            throw new InvalidMessageTypeException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidMessageTypeException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}