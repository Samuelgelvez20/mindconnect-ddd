package com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.entity.ChatConversationJpaEntity;

public interface ChatConversationJpaRepository extends JpaRepository<ChatConversationJpaEntity, UUID> {

    List<ChatConversationJpaEntity> findByConversationStatusId(UUID conversationStatusId);

    List<ChatConversationJpaEntity> findByPriorityId(UUID priorityId);

    List<ChatConversationJpaEntity> findByClosed(boolean closed);
}