package com.mindconnect.domain.ai.chatairunmetrics.model.aggregate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.chatairunmetrics.event.ChatAiRunMetricsDeletedEvent;
import com.mindconnect.domain.ai.chatairunmetrics.event.ChatAiRunMetricsRegisteredEvent;
import com.mindconnect.domain.ai.chatairunmetrics.event.ChatAiRunMetricsUpdatedEvent;
import com.mindconnect.domain.ai.chatairunmetrics.exception.InvalidChatAiRunMetricsException;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

public class ChatAiRunMetrics extends AggregateRoot {

    private final ChatAiRunMetricsId id;
    private ChatAiRunId aiRunId;
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
    private BigDecimal cost;
    private final Instant createdAt;

    private ChatAiRunMetrics(
            ChatAiRunMetricsId id,
            ChatAiRunId aiRunId,
            int promptTokens,
            int completionTokens,
            int totalTokens,
            BigDecimal cost,
            Instant createdAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.aiRunId = Objects.requireNonNull(aiRunId, "aiRunId must not be null");
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
        this.cost = Objects.requireNonNull(cost, "cost must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static ChatAiRunMetrics register(
            ChatAiRunId aiRunId,
            int promptTokens,
            int completionTokens,
            int totalTokens,
            BigDecimal cost) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatAiRunMetricsId id = ChatAiRunMetricsId.generate();

        if (aiRunId == null) {
            throw new InvalidChatAiRunMetricsException("aiRunId must not be null");
        }
        if (cost == null) {
            throw new InvalidChatAiRunMetricsException("cost must not be null");
        }

        ChatAiRunMetrics metrics = new ChatAiRunMetrics(
                id,
                aiRunId,
                promptTokens,
                completionTokens,
                totalTokens,
                cost,
                now);

        metrics.recordEvent(new ChatAiRunMetricsRegisteredEvent(id, now));
        return metrics;
    }

    public static ChatAiRunMetrics restore(
            ChatAiRunMetricsId id,
            ChatAiRunId aiRunId,
            int promptTokens,
            int completionTokens,
            int totalTokens,
            BigDecimal cost,
            Instant createdAt) {

        return new ChatAiRunMetrics(id, aiRunId, promptTokens, completionTokens, totalTokens, cost, createdAt);
    }

    public void update(
            ChatAiRunId aiRunId,
            int promptTokens,
            int completionTokens,
            int totalTokens,
            BigDecimal cost) {

        if (aiRunId == null) {
            throw new InvalidChatAiRunMetricsException("aiRunId must not be null");
        }
        if (cost == null) {
            throw new InvalidChatAiRunMetricsException("cost must not be null");
        }

        this.aiRunId = aiRunId;
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
        this.cost = cost;

        recordEvent(new ChatAiRunMetricsUpdatedEvent(this.id, this.aiRunId, this.promptTokens, this.completionTokens, this.totalTokens, this.cost, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public void delete() {
        recordEvent(new ChatAiRunMetricsDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatAiRunMetricsId id() {
        return id;
    }

    public ChatAiRunId aiRunId() {
        return aiRunId;
    }

    public int promptTokens() {
        return promptTokens;
    }

    public int completionTokens() {
        return completionTokens;
    }

    public int totalTokens() {
        return totalTokens;
    }

    public BigDecimal cost() {
        return cost;
    }

    public Instant createdAt() {
        return createdAt;
    }
}