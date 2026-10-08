package com.mindconnect.domain.chat.chatconversationstatus.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatconversationstatus.event.ChatConversationStatusDeletedEvent;
import com.mindconnect.domain.chat.chatconversationstatus.event.ChatConversationStatusRegisteredEvent;
import com.mindconnect.domain.chat.chatconversationstatus.event.ChatConversationStatusUpdatedEvent;
import com.mindconnect.domain.chat.chatconversationstatus.exception.InvalidChatConversationStatusException;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;

public class ChatConversationStatus extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;

    private final ChatConversationStatusId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatConversationStatus(
            ChatConversationStatusId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatConversationStatus register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatConversationStatusId id = ChatConversationStatusId.generate();

        ChatConversationStatus status = new ChatConversationStatus(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        status.recordEvent(new ChatConversationStatusRegisteredEvent(id, now));
        return status;
    }

    public static ChatConversationStatus restore(
            ChatConversationStatusId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatConversationStatus(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatConversationStatusUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatConversationStatusDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatConversationStatusId id() {
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
            throw new InvalidChatConversationStatusException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidChatConversationStatusException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}