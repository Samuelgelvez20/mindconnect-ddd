package com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatescalationstatus.model.aggregate.ChatEscalationStatus;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.entity.ChatEscalationStatusJpaEntity;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.mappers.ChatEscalationStatusPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatEscalationStatusRepositoryAdapter implements ChatEscalationStatusRepository {

    private final ChatEscalationStatusJpaRepository jpaRepository;
    private final ChatEscalationStatusPersistenceMapper mapper;

    public ChatEscalationStatusRepositoryAdapter(ChatEscalationStatusJpaRepository jpaRepository, ChatEscalationStatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatEscalationStatus save(ChatEscalationStatus status) {
        ChatEscalationStatusJpaEntity entity = mapper.toJpa(status);
        ChatEscalationStatusJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatEscalationStatus> findById(ChatEscalationStatusId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatEscalationStatus> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatEscalationStatus status) {
        jpaRepository.deleteById(status.id().value());
    }

    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, ChatEscalationStatusId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public Optional<ChatEscalationStatus> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }
}