package com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Mirrors table chat_ai_run_errors (migration V48) exactly. */
@Entity
@Table(name = "chat_ai_run_errors", schema = "mindconnect_schema")
public class ChatAiRunErrorJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "ai_run_id", nullable = false)
    private UUID aiRunId;

    @Column(name = "error_message", nullable = false, columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "error_code", length = 80)
    private String errorCode;

    @Column(name = "provider_error_id", length = 120)
    private String providerErrorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ChatAiRunErrorJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAiRunId() {
        return aiRunId;
    }

    public void setAiRunId(UUID aiRunId) {
        this.aiRunId = aiRunId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getProviderErrorId() {
        return providerErrorId;
    }

    public void setProviderErrorId(String providerErrorId) {
        this.providerErrorId = providerErrorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}