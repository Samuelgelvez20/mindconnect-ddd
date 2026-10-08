package com.mindconnect.domain.ai.aimodel.model.aggregate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.aimodel.event.AiModelDeletedEvent;
import com.mindconnect.domain.ai.aimodel.event.AiModelRegisteredEvent;
import com.mindconnect.domain.ai.aimodel.event.AiModelUpdatedEvent;
import com.mindconnect.domain.ai.aimodel.exception.InvalidAiModelException;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

public class AiModel extends AggregateRoot {

    public static final int NAME_MAX_LENGTH = 100;
    public static final int MODEL_KEY_MAX_LENGTH = 120;

    private final AiModelId id;
    private AiProviderId aiProviderId;
    private String name;
    private String modelKey;
    private BigDecimal inputTokenPrice;
    private BigDecimal outputTokenPrice;
    private int maxTokens;
    private int contextWindow;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private AiModel(
            AiModelId id,
            AiProviderId aiProviderId,
            String name,
            String modelKey,
            BigDecimal inputTokenPrice,
            BigDecimal outputTokenPrice,
            int maxTokens,
            int contextWindow,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.aiProviderId = Objects.requireNonNull(aiProviderId, "aiProviderId must not be null");
        this.name = name;
        this.modelKey = modelKey;
        this.inputTokenPrice = inputTokenPrice;
        this.outputTokenPrice = outputTokenPrice;
        this.maxTokens = maxTokens;
        this.contextWindow = contextWindow;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static AiModel register(
            AiProviderId aiProviderId,
            String name,
            String modelKey,
            BigDecimal inputTokenPrice,
            BigDecimal outputTokenPrice,
            int maxTokens,
            int contextWindow) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        AiModelId id = AiModelId.generate();

        if (aiProviderId == null) {
            throw new InvalidAiModelException("aiProviderId must not be null");
        }
        if (inputTokenPrice == null) {
            throw new InvalidAiModelException("inputTokenPrice must not be null");
        }
        if (outputTokenPrice == null) {
            throw new InvalidAiModelException("outputTokenPrice must not be null");
        }

        AiModel model = new AiModel(
                id,
                aiProviderId,
                requiredText(name, "name", NAME_MAX_LENGTH),
                requiredText(modelKey, "modelKey", MODEL_KEY_MAX_LENGTH),
                inputTokenPrice,
                outputTokenPrice,
                maxTokens,
                contextWindow,
                true,
                now,
                now);

        model.recordEvent(new AiModelRegisteredEvent(id, now));
        return model;
    }

    public static AiModel restore(
            AiModelId id,
            AiProviderId aiProviderId,
            String name,
            String modelKey,
            BigDecimal inputTokenPrice,
            BigDecimal outputTokenPrice,
            int maxTokens,
            int contextWindow,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new AiModel(id, aiProviderId, name, modelKey, inputTokenPrice, outputTokenPrice, maxTokens, contextWindow, active, createdAt, updatedAt);
    }

    public void update(
            AiProviderId aiProviderId,
            String name,
            String modelKey,
            BigDecimal inputTokenPrice,
            BigDecimal outputTokenPrice,
            int maxTokens,
            int contextWindow,
            boolean active) {

        if (aiProviderId == null) {
            throw new InvalidAiModelException("aiProviderId must not be null");
        }
        if (inputTokenPrice == null) {
            throw new InvalidAiModelException("inputTokenPrice must not be null");
        }
        if (outputTokenPrice == null) {
            throw new InvalidAiModelException("outputTokenPrice must not be null");
        }

        this.aiProviderId = aiProviderId;
        this.name = requiredText(name, "name", NAME_MAX_LENGTH);
        this.modelKey = requiredText(modelKey, "modelKey", MODEL_KEY_MAX_LENGTH);
        this.inputTokenPrice = inputTokenPrice;
        this.outputTokenPrice = outputTokenPrice;
        this.maxTokens = maxTokens;
        this.contextWindow = contextWindow;
        this.active = active;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new AiModelUpdatedEvent(this.id, this.aiProviderId, this.name, this.modelKey, this.inputTokenPrice, this.outputTokenPrice, this.maxTokens, this.contextWindow, this.active, this.updatedAt));
    }

    public void delete() {
        recordEvent(new AiModelDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public AiModelId id() {
        return id;
    }

    public AiProviderId aiProviderId() {
        return aiProviderId;
    }

    public String name() {
        return name;
    }

    public String modelKey() {
        return modelKey;
    }

    public BigDecimal inputTokenPrice() {
        return inputTokenPrice;
    }

    public BigDecimal outputTokenPrice() {
        return outputTokenPrice;
    }

    public int maxTokens() {
        return maxTokens;
    }

    public int contextWindow() {
        return contextWindow;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidAiModelException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidAiModelException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}