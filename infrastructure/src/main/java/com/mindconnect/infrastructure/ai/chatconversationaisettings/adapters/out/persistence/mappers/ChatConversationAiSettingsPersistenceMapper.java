package com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.mappers;

import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.entity.ChatConversationAiSettingsJpaEntity;

public class ChatConversationAiSettingsPersistenceMapper {

    public ChatConversationAiSettingsJpaEntity toJpa(ChatConversationAiSettings domain) {
        ChatConversationAiSettingsJpaEntity entity = new ChatConversationAiSettingsJpaEntity();
        entity.setId(domain.id().value());
        entity.setConversationId(domain.conversationId().value());
        entity.setAiEnabled(domain.aiEnabled());
        entity.setDefaultModelId(domain.defaultModelId().value());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public ChatConversationAiSettings toDomain(ChatConversationAiSettingsJpaEntity entity) {
        return ChatConversationAiSettings.restore(
                new ChatConversationAiSettingsId(entity.getId()),
                new ChatConversationId(entity.getConversationId()),
                entity.isAiEnabled(),
                new AiModelId(entity.getDefaultModelId()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}