package com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.mappers;

import java.math.BigDecimal;

import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.entity.AiModelJpaEntity;

public class AiModelPersistenceMapper {

    public AiModelJpaEntity toJpa(AiModel domain) {
        AiModelJpaEntity entity = new AiModelJpaEntity();
        entity.setId(domain.id().value());
        entity.setAiProviderId(domain.aiProviderId().value());
        entity.setName(domain.name());
        entity.setModelKey(domain.modelKey());
        entity.setInputTokenPrice(domain.inputTokenPrice());
        entity.setOutputTokenPrice(domain.outputTokenPrice());
        entity.setMaxTokens(domain.maxTokens());
        entity.setContextWindow(domain.contextWindow());
        entity.setActive(domain.active());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public AiModel toDomain(AiModelJpaEntity entity) {
        return AiModel.restore(
                new AiModelId(entity.getId()),
                new AiProviderId(entity.getAiProviderId()),
                entity.getName(),
                entity.getModelKey(),
                entity.getInputTokenPrice(),
                entity.getOutputTokenPrice(),
                entity.getMaxTokens(),
                entity.getContextWindow(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}