package com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Mirrors table chat_escalation_status_history (migration V52) exactly. */
@Entity
@Table(name = "chat_escalation_status_history", schema = "mindconnect_schema")
public class ChatEscalationStatusHistoryJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "escalation_id", nullable = false)
    private UUID escalationId;

    @Column(name = "escalation_status_id", nullable = false)
    private UUID escalationStatusId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    public ChatEscalationStatusHistoryJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEscalationId() {
        return escalationId;
    }

    public void setEscalationId(UUID escalationId) {
        this.escalationId = escalationId;
    }

    public UUID getEscalationStatusId() {
        return escalationStatusId;
    }

    public void setEscalationStatusId(UUID escalationStatusId) {
        this.escalationStatusId = escalationStatusId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }
}