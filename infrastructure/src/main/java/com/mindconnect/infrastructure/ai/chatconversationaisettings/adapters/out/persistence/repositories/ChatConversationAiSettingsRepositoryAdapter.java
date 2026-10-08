package com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.entity.ChatConversationAiSettingsJpaEntity;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.mappers.ChatConversationAiSettingsPersistenceMapper;

public class ChatConversationAiSettingsRepositoryAdapter implements ChatConversationAiSettingsRepository {

    private final ChatConversationAiSettingsJpaRepository jpaRepository;
    private final ChatConversationAiSettingsPersistenceMapper mapper;

    public ChatConversationAiSettingsRepositoryAdapter(
            ChatConversationAiSettingsJpaRepository jpaRepository,
            ChatConversationAiSettingsPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatConversationAiSettings save(ChatConversationAiSettings chatConversationAiSettings) {
        ChatConversationAiSettingsJpaEntity entity = mapper.toJpa(chatConversationAiSettings);
        ChatConversationAiSettingsJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatConversationAiSettings> findById(ChatConversationAiSettingsId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<ChatConversationAiSettings> findByConversationId(ChatConversationId conversationId) {
        return jpaRepository.findByConversationId(conversationId.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<ChatConversationAiSettings> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByConversationId(ChatConversationId conversationId) {
        return jpaRepository.existsByConversationId(conversationId.value());
    }

    @Override
    public boolean existsByConversationIdAndIdNot(ChatConversationId conversationId, ChatConversationAiSettingsId id) {
        return jpaRepository.existsByConversationIdAndIdNot(conversationId.value(), id.value());
    }

    @Override
    public void delete(ChatConversationAiSettings chatConversationAiSettings) {
        jpaRepository.deleteById(chatConversationAiSettings.id().value());
    }
}