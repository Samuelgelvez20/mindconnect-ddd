package com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatmessage.model.aggregate.ChatMessage;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.entity.ChatMessageJpaEntity;
import com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.mappers.ChatMessagePersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatMessageRepositoryAdapter implements ChatMessageRepository {

    private final ChatMessageJpaRepository jpaRepository;
    private final ChatMessagePersistenceMapper mapper;

    public ChatMessageRepositoryAdapter(ChatMessageJpaRepository jpaRepository, ChatMessagePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        ChatMessageJpaEntity entity = mapper.toJpa(message);
        ChatMessageJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatMessage> findById(ChatMessageId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatMessage> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatMessage message) {
        jpaRepository.deleteById(message.id().value());
    }

    public List<ChatMessage> findByConversationId(ChatConversationId conversationId) {
        return jpaRepository.findByConversationId(conversationId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatMessage> findByMessageTypeId(MessageTypeId messageTypeId) {
        return jpaRepository.findByMessageTypeId(messageTypeId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatMessage> findByParticipantId(ChatParticipantId participantId) {
        return jpaRepository.findByParticipantId(participantId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}