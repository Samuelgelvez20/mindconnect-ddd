package com.mindconnect.domain.chat.chatescalation.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatescalation.event.ChatEscalationDeletedEvent;
import com.mindconnect.domain.chat.chatescalation.event.ChatEscalationRegisteredEvent;
import com.mindconnect.domain.chat.chatescalation.event.ChatEscalationUpdatedEvent;
import com.mindconnect.domain.chat.chatescalation.exception.InvalidChatEscalationException;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public class ChatEscalation extends AggregateRoot {

    private final ChatEscalationId id;
    private ChatConversationId conversationId;
    private ChatEscalationStatusId statusId;
    private boolean fromAi;
    private String reason;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatEscalation(
            ChatEscalationId id,
            ChatConversationId conversationId,
            ChatEscalationStatusId statusId,
            boolean fromAi,
            String reason,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.conversationId = conversationId;
        this.statusId = statusId;
        this.fromAi = fromAi;
        this.reason = reason;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatEscalation register(
            ChatConversationId conversationId,
            ChatEscalationStatusId statusId,
            boolean fromAi,
            String reason) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatEscalationId id = ChatEscalationId.generate();

        if (conversationId == null) {
            throw new InvalidChatEscalationException("conversationId must not be null");
        }
        if (statusId == null) {
            throw new InvalidChatEscalationException("statusId must not be null");
        }

        ChatEscalation escalation = new ChatEscalation(
                id,
                conversationId,
                statusId,
                fromAi,
                reason != null ? reason.trim() : null,
                now,
                now);

        escalation.recordEvent(new ChatEscalationRegisteredEvent(id, conversationId, statusId, now));
        return escalation;
    }

    public static ChatEscalation restore(
            ChatEscalationId id,
            ChatConversationId conversationId,
            ChatEscalationStatusId statusId,
            boolean fromAi,
            String reason,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatEscalation(id, conversationId, statusId, fromAi, reason, createdAt, updatedAt);
    }

    public void update(
            ChatConversationId conversationId,
            ChatEscalationStatusId statusId,
            boolean fromAi,
            String reason) {

        this.conversationId = Objects.requireNonNull(conversationId, "conversationId must not be null");
        this.statusId = Objects.requireNonNull(statusId, "statusId must not be null");
        this.fromAi = fromAi;
        this.reason = reason != null ? reason.trim() : null;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatEscalationUpdatedEvent(this.id, this.conversationId, this.statusId, this.fromAi, this.reason, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatEscalationDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatEscalationId id() {
        return id;
    }

    public ChatConversationId conversationId() {
        return conversationId;
    }

    public ChatEscalationStatusId statusId() {
        return statusId;
    }

    public boolean fromAi() {
        return fromAi;
    }

    public String reason() {
        return reason;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}