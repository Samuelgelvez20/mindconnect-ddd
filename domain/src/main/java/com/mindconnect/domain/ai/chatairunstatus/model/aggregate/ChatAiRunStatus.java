package com.mindconnect.domain.ai.chatairunstatus.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.chatairunstatus.event.ChatAiRunStatusDeletedEvent;
import com.mindconnect.domain.ai.chatairunstatus.event.ChatAiRunStatusRegisteredEvent;
import com.mindconnect.domain.ai.chatairunstatus.event.ChatAiRunStatusUpdatedEvent;
import com.mindconnect.domain.ai.chatairunstatus.exception.InvalidChatAiRunStatusException;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

public class ChatAiRunStatus extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 50;

    private final ChatAiRunStatusId id;
    private String name;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatAiRunStatus(
            ChatAiRunStatusId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = name;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatAiRunStatus register(String name) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatAiRunStatusId id = ChatAiRunStatusId.generate();

        ChatAiRunStatus status = new ChatAiRunStatus(
                id,
                requiredText(name, "name", NAME_MAX_LENGTH),
                now,
                now);

        status.recordEvent(new ChatAiRunStatusRegisteredEvent(id, now));
        return status;
    }

    public static ChatAiRunStatus restore(
            ChatAiRunStatusId id,
            String name,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatAiRunStatus(id, name, createdAt, updatedAt);
    }

    public void update(String name) {

        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatAiRunStatusUpdatedEvent(this.id, this.name, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatAiRunStatusDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatAiRunStatusId id() {
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
            throw new InvalidChatAiRunStatusException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidChatAiRunStatusException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}