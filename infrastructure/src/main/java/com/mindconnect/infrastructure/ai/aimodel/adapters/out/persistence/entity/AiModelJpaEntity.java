package com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Mirrors table ai_models (migration V43) exactly. */
@Entity
@Table(name = "ai_models", schema = "mindconnect_schema")
public class AiModelJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "ai_provider_id", nullable = false)
    private UUID aiProviderId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "model_key", nullable = false, length = 120, unique = true)
    private String modelKey;

    @Column(name = "input_token_price", nullable = false, precision = 12, scale = 8)
    private BigDecimal inputTokenPrice;

    @Column(name = "output_token_price", nullable = false, precision = 12, scale = 8)
    private BigDecimal outputTokenPrice;

    @Column(name = "max_tokens", nullable = false)
    private int maxTokens;

    @Column(name = "context_window", nullable = false)
    private int contextWindow;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public AiModelJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAiProviderId() {
        return aiProviderId;
    }

    public void setAiProviderId(UUID aiProviderId) {
        this.aiProviderId = aiProviderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public BigDecimal getInputTokenPrice() {
        return inputTokenPrice;
    }

    public void setInputTokenPrice(BigDecimal inputTokenPrice) {
        this.inputTokenPrice = inputTokenPrice;
    }

    public BigDecimal getOutputTokenPrice() {
        return outputTokenPrice;
    }

    public void setOutputTokenPrice(BigDecimal outputTokenPrice) {
        this.outputTokenPrice = outputTokenPrice;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public int getContextWindow() {
        return contextWindow;
    }

    public void setContextWindow(int contextWindow) {
        this.contextWindow = contextWindow;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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