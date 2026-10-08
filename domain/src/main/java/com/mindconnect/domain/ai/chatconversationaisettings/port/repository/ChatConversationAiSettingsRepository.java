package com.mindconnect.domain.ai.chatconversationaisettings.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;

public interface ChatConversationAiSettingsRepository {

    ChatConversationAiSettings save(ChatConversationAiSettings chatConversationAiSettings);

    Optional<ChatConversationAiSettings> findById(ChatConversationAiSettingsId id);

    Optional<ChatConversationAiSettings> findByConversationId(ChatConversationId conversationId);

    List<ChatConversationAiSettings> findAll();

    boolean existsByConversationId(ChatConversationId conversationId);

    boolean existsByConversationIdAndIdNot(ChatConversationId conversationId, ChatConversationAiSettingsId id);

    void delete(ChatConversationAiSettings chatConversationAiSettings);
}