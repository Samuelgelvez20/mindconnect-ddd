package com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.entity.ChatEscalationStatusHistoryJpaEntity;
import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.mappers.ChatEscalationStatusHistoryPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatEscalationStatusHistoryRepositoryAdapter implements ChatEscalationStatusHistoryRepository {

    private final ChatEscalationStatusHistoryJpaRepository jpaRepository;
    private final ChatEscalationStatusHistoryPersistenceMapper mapper;

    public ChatEscalationStatusHistoryRepositoryAdapter(ChatEscalationStatusHistoryJpaRepository jpaRepository, ChatEscalationStatusHistoryPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatEscalationStatusHistory save(ChatEscalationStatusHistory history) {
        ChatEscalationStatusHistoryJpaEntity entity = mapper.toJpa(history);
        ChatEscalationStatusHistoryJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatEscalationStatusHistory> findById(ChatEscalationStatusHistoryId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatEscalationStatusHistory> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatEscalationStatusHistory history) {
        jpaRepository.deleteById(history.id().value());
    }

    public List<ChatEscalationStatusHistory> findByEscalationId(ChatEscalationId escalationId) {
        return jpaRepository.findByEscalationId(escalationId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatEscalationStatusHistory> findByEscalationStatusId(ChatEscalationStatusId escalationStatusId) {
        return jpaRepository.findByEscalationStatusId(escalationStatusId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}