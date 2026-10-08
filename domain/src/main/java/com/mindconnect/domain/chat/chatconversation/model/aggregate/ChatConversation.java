package com.mindconnect.domain.chat.chatconversation.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatconversation.event.ChatConversationDeletedEvent;
import com.mindconnect.domain.chat.chatconversation.event.ChatConversationRegisteredEvent;
import com.mindconnect.domain.chat.chatconversation.event.ChatConversationUpdatedEvent;
import com.mindconnect.domain.chat.chatconversation.exception.InvalidChatConversationException;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public class ChatConversation extends AggregateRoot {

    private final ChatConversationId id;
    private ChatConversationStatusId conversationStatusId;
    private PriorityId priorityId;
    private Instant lastMessageAt;
    private boolean closed;
    private Instant closedAt;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatConversation(
            ChatConversationId id,
            ChatConversationStatusId conversationStatusId,
            PriorityId priorityId,
            Instant lastMessageAt,
            boolean closed,
            Instant closedAt,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.conversationStatusId = conversationStatusId;
        this.priorityId = priorityId;
        this.lastMessageAt = lastMessageAt;
        this.closed = closed;
        this.closedAt = closedAt;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatConversation register(
            ChatConversationStatusId conversationStatusId,
            PriorityId priorityId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatConversationId id = ChatConversationId.generate();

        if (conversationStatusId == null) {
            throw new InvalidChatConversationException("conversationStatusId must not be null");
        }
        if (priorityId == null) {
            throw new InvalidChatConversationException("priorityId must not be null");
        }

        ChatConversation conversation = new ChatConversation(
                id,
                conversationStatusId,
                priorityId,
                null,
                false,
                null,
                now,
                now);

        conversation.recordEvent(new ChatConversationRegisteredEvent(id, now));
        return conversation;
    }

    public static ChatConversation restore(
            ChatConversationId id,
            ChatConversationStatusId conversationStatusId,
            PriorityId priorityId,
            Instant lastMessageAt,
            boolean closed,
            Instant closedAt,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatConversation(id, conversationStatusId, priorityId, lastMessageAt, closed, closedAt, createdAt, updatedAt);
    }

    public void update(
            ChatConversationStatusId conversationStatusId,
            PriorityId priorityId,
            Instant lastMessageAt,
            boolean closed,
            Instant closedAt) {

        this.conversationStatusId = Objects.requireNonNull(conversationStatusId, "conversationStatusId must not be null");
        this.priorityId = Objects.requireNonNull(priorityId, "priorityId must not be null");
        this.lastMessageAt = lastMessageAt;
        this.closed = closed;
        this.closedAt = closedAt;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatConversationUpdatedEvent(this.id, this.conversationStatusId, this.priorityId, this.lastMessageAt, this.closed, this.closedAt, this.updatedAt, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatConversationDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatConversationId id() {
        return id;
    }

    public ChatConversationStatusId conversationStatusId() {
        return conversationStatusId;
    }

    public PriorityId priorityId() {
        return priorityId;
    }

    public Instant lastMessageAt() {
        return lastMessageAt;
    }

    public boolean isClosed() {
        return closed;
    }

    public Instant closedAt() {
        return closedAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}