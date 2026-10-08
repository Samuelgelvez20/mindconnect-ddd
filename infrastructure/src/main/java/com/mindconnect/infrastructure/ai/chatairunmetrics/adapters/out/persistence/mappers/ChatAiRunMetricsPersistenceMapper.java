package com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.mappers;

import java.math.BigDecimal;

import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.entity.ChatAiRunMetricsJpaEntity;

public class ChatAiRunMetricsPersistenceMapper {

    public ChatAiRunMetricsJpaEntity toJpa(ChatAiRunMetrics domain) {
        ChatAiRunMetricsJpaEntity entity = new ChatAiRunMetricsJpaEntity();
        entity.setId(domain.id().value());
        entity.setAiRunId(domain.aiRunId().value());
        entity.setPromptTokens(domain.promptTokens());
        entity.setCompletionTokens(domain.completionTokens());
        entity.setTotalTokens(domain.totalTokens());
        entity.setCost(domain.cost());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }

    public ChatAiRunMetrics toDomain(ChatAiRunMetricsJpaEntity entity) {
        return ChatAiRunMetrics.restore(
                new ChatAiRunMetricsId(entity.getId()),
                new ChatAiRunId(entity.getAiRunId()),
                entity.getPromptTokens(),
                entity.getCompletionTokens(),
                entity.getTotalTokens(),
                entity.getCost(),
                entity.getCreatedAt());
    }
}