package com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.entity.ChatEscalationJpaEntity;

public interface ChatEscalationJpaRepository extends JpaRepository<ChatEscalationJpaEntity, UUID> {

    List<ChatEscalationJpaEntity> findByConversationId(UUID conversationId);

    List<ChatEscalationJpaEntity> findByStatusId(UUID statusId);
}