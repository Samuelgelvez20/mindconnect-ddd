package com.mindconnect.domain.chat.chatconversation.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.chatconversation.model.aggregate.ChatConversation;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;

public interface ChatConversationRepository {

    ChatConversation save(ChatConversation chatConversation);

    Optional<ChatConversation> findById(ChatConversationId id);

    List<ChatConversation> findAll();

    void delete(ChatConversation chatConversation);
}