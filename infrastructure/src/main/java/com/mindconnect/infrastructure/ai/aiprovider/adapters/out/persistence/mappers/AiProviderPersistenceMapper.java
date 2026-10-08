package com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.mappers;

import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.entity.AiProviderJpaEntity;

public class AiProviderPersistenceMapper {

    public AiProviderJpaEntity toJpa(AiProvider domain) {
        AiProviderJpaEntity entity = new AiProviderJpaEntity();
        entity.setId(domain.id().value());
        entity.setName(domain.name());
        entity.setLegalName(domain.legalName());
        entity.setWebsite(domain.website());
        entity.setActive(domain.active());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public AiProvider toDomain(AiProviderJpaEntity entity) {
        return AiProvider.restore(
                new AiProviderId(entity.getId()),
                entity.getName(),
                entity.getLegalName(),
                entity.getWebsite(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}