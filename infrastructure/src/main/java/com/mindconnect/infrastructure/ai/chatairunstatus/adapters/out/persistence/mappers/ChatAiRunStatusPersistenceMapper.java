package com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.mappers;

import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.entity.ChatAiRunStatusJpaEntity;

public class ChatAiRunStatusPersistenceMapper {

    public ChatAiRunStatusJpaEntity toJpa(ChatAiRunStatus domain) {
        ChatAiRunStatusJpaEntity entity = new ChatAiRunStatusJpaEntity();
        entity.setId(domain.id().value());
        entity.setName(domain.name());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public ChatAiRunStatus toDomain(ChatAiRunStatusJpaEntity entity) {
        return ChatAiRunStatus.restore(
                new ChatAiRunStatusId(entity.getId()),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}