package com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/** Mirrors table chat_escalation_assignments (migration V51) exactly. */
@Entity
@Table(name = "chat_escalation_assignments", schema = "mindconnect_schema",
        uniqueConstraints = @UniqueConstraint(name = "uk_chat_escalation_assignments_escalation_professional", columnNames = {"escalation_id", "professional_id"}))
public class ChatEscalationAssignmentJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "escalation_id", nullable = false)
    private UUID escalationId;

    @Column(name = "professional_id", nullable = false)
    private UUID professionalId;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    public ChatEscalationAssignmentJpaEntity() {
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

    public UUID getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        this.professionalId = professionalId;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }
}