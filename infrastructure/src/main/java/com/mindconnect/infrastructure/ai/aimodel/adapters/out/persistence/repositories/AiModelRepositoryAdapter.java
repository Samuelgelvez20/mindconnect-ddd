package com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;
import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.entity.AiModelJpaEntity;
import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.mappers.AiModelPersistenceMapper;

public class AiModelRepositoryAdapter implements AiModelRepository {

    private final AiModelJpaRepository jpaRepository;
    private final AiModelPersistenceMapper mapper;

    public AiModelRepositoryAdapter(
            AiModelJpaRepository jpaRepository,
            AiModelPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AiModel save(AiModel aiModel) {
        AiModelJpaEntity entity = mapper.toJpa(aiModel);
        AiModelJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<AiModel> findById(AiModelId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<AiModel> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByModelKey(String modelKey) {
        return jpaRepository.existsByModelKey(modelKey);
    }

    @Override
    public boolean existsByModelKeyAndIdNot(String modelKey, AiModelId id) {
        return jpaRepository.existsByModelKeyAndIdNot(modelKey, id.value());
    }

    @Override
    public void delete(AiModel aiModel) {
        jpaRepository.deleteById(aiModel.id().value());
    }
}