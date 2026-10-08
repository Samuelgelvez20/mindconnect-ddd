package com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;
import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.entity.ChatAiRunJpaEntity;
import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.mappers.ChatAiRunPersistenceMapper;

public class ChatAiRunRepositoryAdapter implements ChatAiRunRepository {

    private final ChatAiRunJpaRepository jpaRepository;
    private final ChatAiRunPersistenceMapper mapper;

    public ChatAiRunRepositoryAdapter(
            ChatAiRunJpaRepository jpaRepository,
            ChatAiRunPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatAiRun save(ChatAiRun chatAiRun) {
        ChatAiRunJpaEntity entity = mapper.toJpa(chatAiRun);
        ChatAiRunJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatAiRun> findById(ChatAiRunId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<ChatAiRun> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatAiRun chatAiRun) {
        jpaRepository.deleteById(chatAiRun.id().value());
    }
}