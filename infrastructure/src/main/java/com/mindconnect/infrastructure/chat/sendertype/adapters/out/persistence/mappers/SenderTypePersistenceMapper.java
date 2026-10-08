package com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.sendertype.model.aggregate.SenderType;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.entity.SenderTypeJpaEntity;

public class SenderTypePersistenceMapper {

    public SenderTypeJpaEntity toJpa(SenderType domain) {
        if (domain == null) {
            return null;
        }

        SenderTypeJpaEntity jpa = new SenderTypeJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public SenderType toDomain(SenderTypeJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return SenderType.restore(
                new SenderTypeId(jpa.getId()),
                jpa.getName(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}