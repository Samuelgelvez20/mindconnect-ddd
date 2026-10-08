package com.mindconnect.domain.ai.chatconversationaisettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;

public class FakeChatConversationAiSettingsRepository implements ChatConversationAiSettingsRepository {

    private final ConcurrentMap<UUID, ChatConversationAiSettings> store = new ConcurrentHashMap<>();

    @Override
    public ChatConversationAiSettings save(ChatConversationAiSettings chatConversationAiSettings) {
        store.put(chatConversationAiSettings.id().value(), chatConversationAiSettings);
        return chatConversationAiSettings;
    }

    @Override
    public Optional<ChatConversationAiSettings> findById(ChatConversationAiSettingsId id) {
        return Optional.ofNullable(store.get(id.value()));
    }

    @Override
    public Optional<ChatConversationAiSettings> findByConversationId(ChatConversationId conversationId) {
        return store.values().stream()
                .filter(s -> s.conversationId().equals(conversationId))
                .findFirst();
    }

    @Override
    public List<ChatConversationAiSettings> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByConversationId(ChatConversationId conversationId) {
        return store.values().stream()
                .anyMatch(s -> s.conversationId().equals(conversationId));
    }

    @Override
    public boolean existsByConversationIdAndIdNot(ChatConversationId conversationId, ChatConversationAiSettingsId id) {
        return store.values().stream()
                .anyMatch(s -> s.conversationId().equals(conversationId) && !s.id().equals(id));
    }

    @Override
    public void delete(ChatConversationAiSettings chatConversationAiSettings) {
        store.remove(chatConversationAiSettings.id().value());
    }
}