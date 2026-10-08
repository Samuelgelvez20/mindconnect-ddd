package com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.entity.ChatAiRunErrorJpaEntity;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.mappers.ChatAiRunErrorPersistenceMapper;

public class ChatAiRunErrorRepositoryAdapter implements ChatAiRunErrorRepository {

    private final ChatAiRunErrorJpaRepository jpaRepository;
    private final ChatAiRunErrorPersistenceMapper mapper;

    public ChatAiRunErrorRepositoryAdapter(
            ChatAiRunErrorJpaRepository jpaRepository,
            ChatAiRunErrorPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatAiRunError save(ChatAiRunError chatAiRunError) {
        ChatAiRunErrorJpaEntity entity = mapper.toJpa(chatAiRunError);
        ChatAiRunErrorJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatAiRunError> findById(ChatAiRunErrorId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<ChatAiRunError> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatAiRunError chatAiRunError) {
        jpaRepository.deleteById(chatAiRunError.id().value());
    }
}