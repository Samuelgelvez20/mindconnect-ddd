package com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.chatconversation.model.aggregate.ChatConversation;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.entity.ChatConversationJpaEntity;

public class ChatConversationPersistenceMapper {

    public ChatConversationJpaEntity toJpa(ChatConversation domain) {
        if (domain == null) {
            return null;
        }

        ChatConversationJpaEntity jpa = new ChatConversationJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setConversationStatusId(domain.conversationStatusId().value());
        jpa.setPriorityId(domain.priorityId().value());
        jpa.setLastMessageAt(domain.lastMessageAt());
        jpa.setClosed(domain.isClosed());
        jpa.setClosedAt(domain.closedAt());
        jpa.setClosedBy(null);
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public ChatConversation toDomain(ChatConversationJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return ChatConversation.restore(
                new ChatConversationId(jpa.getId()),
                new ChatConversationStatusId(jpa.getConversationStatusId()),
                new PriorityId(jpa.getPriorityId()),
                jpa.getLastMessageAt(),
                jpa.isClosed(),
                jpa.getClosedAt(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}