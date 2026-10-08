package com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.entity.ChatAiRunStatusJpaEntity;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.mappers.ChatAiRunStatusPersistenceMapper;

public class ChatAiRunStatusRepositoryAdapter implements ChatAiRunStatusRepository {

    private final ChatAiRunStatusJpaRepository jpaRepository;
    private final ChatAiRunStatusPersistenceMapper mapper;

    public ChatAiRunStatusRepositoryAdapter(
            ChatAiRunStatusJpaRepository jpaRepository,
            ChatAiRunStatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatAiRunStatus save(ChatAiRunStatus chatAiRunStatus) {
        ChatAiRunStatusJpaEntity entity = mapper.toJpa(chatAiRunStatus);
        ChatAiRunStatusJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatAiRunStatus> findById(ChatAiRunStatusId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<ChatAiRunStatus> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public List<ChatAiRunStatus> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, ChatAiRunStatusId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public void delete(ChatAiRunStatus chatAiRunStatus) {
        jpaRepository.deleteById(chatAiRunStatus.id().value());
    }
}