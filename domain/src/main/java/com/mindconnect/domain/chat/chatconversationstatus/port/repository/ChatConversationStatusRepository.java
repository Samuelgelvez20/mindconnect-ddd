package com.mindconnect.domain.chat.chatconversationstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.chatconversationstatus.model.aggregate.ChatConversationStatus;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;

public interface ChatConversationStatusRepository {

    ChatConversationStatus save(ChatConversationStatus chatConversationStatus);

    Optional<ChatConversationStatus> findById(ChatConversationStatusId id);

    Optional<ChatConversationStatus> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, ChatConversationStatusId id);

    List<ChatConversationStatus> findAll();

    void delete(ChatConversationStatus chatConversationStatus);
}