package com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.chatconversationstatus.model.aggregate.ChatConversationStatus;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.entity.ChatConversationStatusJpaEntity;

public class ChatConversationStatusPersistenceMapper {

    public ChatConversationStatusJpaEntity toJpa(ChatConversationStatus domain) {
        if (domain == null) {
            return null;
        }

        ChatConversationStatusJpaEntity jpa = new ChatConversationStatusJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public ChatConversationStatus toDomain(ChatConversationStatusJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return ChatConversationStatus.restore(
                new ChatConversationStatusId(jpa.getId()),
                jpa.getName(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}