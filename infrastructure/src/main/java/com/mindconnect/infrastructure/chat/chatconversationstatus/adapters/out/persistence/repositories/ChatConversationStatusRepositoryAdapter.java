package com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatconversationstatus.model.aggregate.ChatConversationStatus;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.entity.ChatConversationStatusJpaEntity;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.mappers.ChatConversationStatusPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatConversationStatusRepositoryAdapter implements ChatConversationStatusRepository {

    private final ChatConversationStatusJpaRepository jpaRepository;
    private final ChatConversationStatusPersistenceMapper mapper;

    public ChatConversationStatusRepositoryAdapter(ChatConversationStatusJpaRepository jpaRepository, ChatConversationStatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatConversationStatus save(ChatConversationStatus status) {
        ChatConversationStatusJpaEntity entity = mapper.toJpa(status);
        ChatConversationStatusJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatConversationStatus> findById(ChatConversationStatusId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatConversationStatus> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatConversationStatus status) {
        jpaRepository.deleteById(status.id().value());
    }

    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, ChatConversationStatusId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public Optional<ChatConversationStatus> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }
}