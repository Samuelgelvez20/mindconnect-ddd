package com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.messagetype.model.aggregate.MessageType;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.entity.MessageTypeJpaEntity;

public class MessageTypePersistenceMapper {

    public MessageTypeJpaEntity toJpa(MessageType domain) {
        if (domain == null) {
            return null;
        }

        MessageTypeJpaEntity jpa = new MessageTypeJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public MessageType toDomain(MessageTypeJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return MessageType.restore(
                new MessageTypeId(jpa.getId()),
                jpa.getName(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}