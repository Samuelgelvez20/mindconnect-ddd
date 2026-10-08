package com.mindconnect.domain.chat.chatescalationstatus.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatescalationstatus.event.ChatEscalationStatusDeletedEvent;
import com.mindconnect.domain.chat.chatescalationstatus.event.ChatEscalationStatusRegisteredEvent;
import com.mindconnect.domain.chat.chatescalationstatus.event.ChatEscalationStatusUpdatedEvent;
import com.mindconnect.domain.chat.chatescalationstatus.exception.InvalidChatEscalationStatusException;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class ChatEscalationStatus extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;

    private final ChatEscalationStatusId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatEscalationStatus(
            ChatEscalationStatusId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatEscalationStatus register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatEscalationStatusId id = ChatEscalationStatusId.generate();

        ChatEscalationStatus status = new ChatEscalationStatus(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        status.recordEvent(new ChatEscalationStatusRegisteredEvent(id, now));
        return status;
    }

    public static ChatEscalationStatus restore(
            ChatEscalationStatusId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatEscalationStatus(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatEscalationStatusUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatEscalationStatusDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatEscalationStatusId id() {
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
            throw new InvalidChatEscalationStatusException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidChatEscalationStatusException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}