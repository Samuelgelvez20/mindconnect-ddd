package com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.entity.ChatMessageJpaEntity;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageJpaEntity, UUID> {

    List<ChatMessageJpaEntity> findByConversationId(UUID conversationId);

    List<ChatMessageJpaEntity> findByMessageTypeId(UUID messageTypeId);

    List<ChatMessageJpaEntity> findByParticipantId(UUID participantId);
}