package com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatescalation.model.aggregate.ChatEscalation;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.entity.ChatEscalationJpaEntity;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.mappers.ChatEscalationPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatEscalationRepositoryAdapter implements ChatEscalationRepository {

    private final ChatEscalationJpaRepository jpaRepository;
    private final ChatEscalationPersistenceMapper mapper;

    public ChatEscalationRepositoryAdapter(ChatEscalationJpaRepository jpaRepository, ChatEscalationPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatEscalation save(ChatEscalation escalation) {
        ChatEscalationJpaEntity entity = mapper.toJpa(escalation);
        ChatEscalationJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatEscalation> findById(ChatEscalationId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatEscalation> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatEscalation escalation) {
        jpaRepository.deleteById(escalation.id().value());
    }

    public List<ChatEscalation> findByConversationId(ChatConversationId conversationId) {
        return jpaRepository.findByConversationId(conversationId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatEscalation> findByStatusId(ChatEscalationStatusId statusId) {
        return jpaRepository.findByStatusId(statusId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}