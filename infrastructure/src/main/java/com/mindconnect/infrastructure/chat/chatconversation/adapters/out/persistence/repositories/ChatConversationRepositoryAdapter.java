package com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatconversation.model.aggregate.ChatConversation;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.entity.ChatConversationJpaEntity;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.mappers.ChatConversationPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatConversationRepositoryAdapter implements ChatConversationRepository {

    private final ChatConversationJpaRepository jpaRepository;
    private final ChatConversationPersistenceMapper mapper;

    public ChatConversationRepositoryAdapter(ChatConversationJpaRepository jpaRepository, ChatConversationPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatConversation save(ChatConversation conversation) {
        ChatConversationJpaEntity entity = mapper.toJpa(conversation);
        ChatConversationJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatConversation> findById(ChatConversationId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatConversation> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatConversation conversation) {
        jpaRepository.deleteById(conversation.id().value());
    }

    public List<ChatConversation> findByConversationStatusId(ChatConversationStatusId conversationStatusId) {
        return jpaRepository.findByConversationStatusId(conversationStatusId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatConversation> findByPriorityId(PriorityId priorityId) {
        return jpaRepository.findByPriorityId(priorityId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatConversation> findByClosed(boolean closed) {
        return jpaRepository.findByClosed(closed).stream()
                .map(mapper::toDomain)
                .toList();
    }
}