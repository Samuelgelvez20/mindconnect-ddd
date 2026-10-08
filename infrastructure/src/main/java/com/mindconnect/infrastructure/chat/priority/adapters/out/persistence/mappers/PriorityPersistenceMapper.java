package com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.priority.model.aggregate.Priority;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.entity.PriorityJpaEntity;

public class PriorityPersistenceMapper {

    public PriorityJpaEntity toJpa(Priority domain) {
        if (domain == null) {
            return null;
        }

        PriorityJpaEntity jpa = new PriorityJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setName(domain.name());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public Priority toDomain(PriorityJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return Priority.restore(
                new PriorityId(jpa.getId()),
                jpa.getName(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}