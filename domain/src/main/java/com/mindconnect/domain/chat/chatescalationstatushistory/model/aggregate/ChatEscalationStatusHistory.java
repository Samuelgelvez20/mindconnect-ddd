package com.mindconnect.domain.chat.chatescalationstatushistory.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatescalationstatushistory.event.ChatEscalationStatusHistoryDeletedEvent;
import com.mindconnect.domain.chat.chatescalationstatushistory.event.ChatEscalationStatusHistoryRegisteredEvent;
import com.mindconnect.domain.chat.chatescalationstatushistory.event.ChatEscalationStatusHistoryUpdatedEvent;
import com.mindconnect.domain.chat.chatescalationstatushistory.exception.InvalidChatEscalationStatusHistoryException;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class ChatEscalationStatusHistory extends AggregateRoot {

    private final ChatEscalationStatusHistoryId id;
    private ChatEscalationId escalationId;
    private ChatEscalationStatusId escalationStatusId;
    private Instant changedAt;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatEscalationStatusHistory(
            ChatEscalationStatusHistoryId id,
            ChatEscalationId escalationId,
            ChatEscalationStatusId escalationStatusId,
            Instant changedAt,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.escalationId = escalationId;
        this.escalationStatusId = escalationStatusId;
        this.changedAt = changedAt;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatEscalationStatusHistory register(
            ChatEscalationId escalationId,
            ChatEscalationStatusId escalationStatusId,
            Instant changedAt) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatEscalationStatusHistoryId id = ChatEscalationStatusHistoryId.generate();

        if (escalationId == null) {
            throw new InvalidChatEscalationStatusHistoryException("escalationId must not be null");
        }
        if (escalationStatusId == null) {
            throw new InvalidChatEscalationStatusHistoryException("escalationStatusId must not be null");
        }
        if (changedAt == null) {
            throw new InvalidChatEscalationStatusHistoryException("changedAt must not be null");
        }

        ChatEscalationStatusHistory history = new ChatEscalationStatusHistory(
                id,
                escalationId,
                escalationStatusId,
                changedAt,
                now,
                now);

        history.recordEvent(new ChatEscalationStatusHistoryRegisteredEvent(id, now));
        return history;
    }

    public static ChatEscalationStatusHistory restore(
            ChatEscalationStatusHistoryId id,
            ChatEscalationId escalationId,
            ChatEscalationStatusId escalationStatusId,
            Instant changedAt,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatEscalationStatusHistory(id, escalationId, escalationStatusId, changedAt, createdAt, updatedAt);
    }

    public void update(
            ChatEscalationId escalationId,
            ChatEscalationStatusId escalationStatusId,
            Instant changedAt) {

        this.escalationId = Objects.requireNonNull(escalationId, "escalationId must not be null");
        this.escalationStatusId = Objects.requireNonNull(escalationStatusId, "escalationStatusId must not be null");
        this.changedAt = Objects.requireNonNull(changedAt, "changedAt must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatEscalationStatusHistoryUpdatedEvent(this.id, this.escalationId, this.escalationStatusId, this.changedAt, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatEscalationStatusHistoryDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatEscalationStatusHistoryId id() {
        return id;
    }

    public ChatEscalationId escalationId() {
        return escalationId;
    }

    public ChatEscalationStatusId escalationStatusId() {
        return escalationStatusId;
    }

    public Instant changedAt() {
        return changedAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}