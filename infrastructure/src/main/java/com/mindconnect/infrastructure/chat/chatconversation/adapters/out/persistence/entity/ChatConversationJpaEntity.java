package com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Mirrors table chat_conversations (migration V39) exactly. */
@Entity
@Table(name = "chat_conversations", schema = "mindconnect_schema")
public class ChatConversationJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "conversation_status_id", nullable = false)
    private UUID conversationStatusId;

    @Column(name = "priority_id", nullable = false)
    private UUID priorityId;

    @Column(name = "last_message_at")
    private Instant lastMessageAt;

    @Column(name = "closed", nullable = false)
    private boolean closed;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Column(name = "closed_by")
    private UUID closedBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public ChatConversationJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getConversationStatusId() {
        return conversationStatusId;
    }

    public void setConversationStatusId(UUID conversationStatusId) {
        this.conversationStatusId = conversationStatusId;
    }

    public UUID getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(UUID priorityId) {
        this.priorityId = priorityId;
    }

    public Instant getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(Instant lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public UUID getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(UUID closedBy) {
        this.closedBy = closedBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}