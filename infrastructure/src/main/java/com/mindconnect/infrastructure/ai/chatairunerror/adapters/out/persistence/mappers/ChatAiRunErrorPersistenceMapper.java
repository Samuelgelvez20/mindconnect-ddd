package com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.mappers;

import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.entity.ChatAiRunErrorJpaEntity;

public class ChatAiRunErrorPersistenceMapper {

    public ChatAiRunErrorJpaEntity toJpa(ChatAiRunError domain) {
        ChatAiRunErrorJpaEntity entity = new ChatAiRunErrorJpaEntity();
        entity.setId(domain.id().value());
        entity.setAiRunId(domain.aiRunId().value());
        entity.setErrorMessage(domain.errorMessage());
        entity.setErrorCode(domain.errorCode());
        entity.setProviderErrorId(domain.providerErrorId());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }

    public ChatAiRunError toDomain(ChatAiRunErrorJpaEntity entity) {
        return ChatAiRunError.restore(
                new ChatAiRunErrorId(entity.getId()),
                new ChatAiRunId(entity.getAiRunId()),
                entity.getErrorMessage(),
                entity.getErrorCode(),
                entity.getProviderErrorId(),
                entity.getCreatedAt());
    }
}