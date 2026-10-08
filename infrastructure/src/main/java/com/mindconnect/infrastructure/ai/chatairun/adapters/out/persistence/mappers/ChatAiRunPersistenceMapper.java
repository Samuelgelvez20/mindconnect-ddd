package com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.mappers;

import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.entity.ChatAiRunJpaEntity;

public class ChatAiRunPersistenceMapper {

    public ChatAiRunJpaEntity toJpa(ChatAiRun domain) {
        ChatAiRunJpaEntity entity = new ChatAiRunJpaEntity();
        entity.setId(domain.id().value());
        entity.setConversationId(domain.conversationId().value());
        entity.setMessageId(domain.messageId().value());
        entity.setModelId(domain.modelId().value());
        entity.setAiRunStatusId(domain.aiRunStatusId().value());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public ChatAiRun toDomain(ChatAiRunJpaEntity entity) {
        return ChatAiRun.restore(
                new ChatAiRunId(entity.getId()),
                new ChatConversationId(entity.getConversationId()),
                new ChatMessageId(entity.getMessageId()),
                new AiModelId(entity.getModelId()),
                new ChatAiRunStatusId(entity.getAiRunStatusId()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}