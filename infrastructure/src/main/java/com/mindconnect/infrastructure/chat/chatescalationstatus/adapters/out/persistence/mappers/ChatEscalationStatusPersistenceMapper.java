package com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.chatescalationstatus.model.aggregate.ChatEscalationStatus;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.entity.ChatEscalationStatusJpaEntity;

public class ChatEscalationStatusPersistenceMapper {

    public ChatEscalationStatusJpaEntity toJpa(ChatEscalationStatus domain) {
        if (domain == null) {
            return null;
        }

        ChatEscalationStatusJpaEntity jpa = new ChatEscalationStatusJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public ChatEscalationStatus toDomain(ChatEscalationStatusJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return ChatEscalationStatus.restore(
                new ChatEscalationStatusId(jpa.getId()),
                jpa.getName(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}