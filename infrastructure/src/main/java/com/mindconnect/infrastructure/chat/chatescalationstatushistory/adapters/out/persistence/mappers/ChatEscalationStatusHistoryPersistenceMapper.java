package com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.entity.ChatEscalationStatusHistoryJpaEntity;

public class ChatEscalationStatusHistoryPersistenceMapper {

    public ChatEscalationStatusHistoryJpaEntity toJpa(ChatEscalationStatusHistory domain) {
        if (domain == null) {
            return null;
        }

        ChatEscalationStatusHistoryJpaEntity jpa = new ChatEscalationStatusHistoryJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setEscalationId(domain.escalationId().value());
        jpa.setEscalationStatusId(domain.escalationStatusId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setChangedAt(domain.changedAt());
        return jpa;
    }

    public ChatEscalationStatusHistory toDomain(ChatEscalationStatusHistoryJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return ChatEscalationStatusHistory.restore(
                new ChatEscalationStatusHistoryId(jpa.getId()),
                new ChatEscalationId(jpa.getEscalationId()),
                new ChatEscalationStatusId(jpa.getEscalationStatusId()),
                jpa.getChangedAt(),
                jpa.getCreatedAt(),
                jpa.getChangedAt());
    }
}