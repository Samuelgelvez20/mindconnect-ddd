package com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.entity.AiProviderJpaEntity;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.mappers.AiProviderPersistenceMapper;

public class AiProviderRepositoryAdapter implements AiProviderRepository {

    private final AiProviderJpaRepository jpaRepository;
    private final AiProviderPersistenceMapper mapper;

    public AiProviderRepositoryAdapter(
            AiProviderJpaRepository jpaRepository,
            AiProviderPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AiProvider save(AiProvider aiProvider) {
        AiProviderJpaEntity entity = mapper.toJpa(aiProvider);
        AiProviderJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<AiProvider> findById(AiProviderId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<AiProvider> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, AiProviderId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public void delete(AiProvider aiProvider) {
        jpaRepository.deleteById(aiProvider.id().value());
    }
}