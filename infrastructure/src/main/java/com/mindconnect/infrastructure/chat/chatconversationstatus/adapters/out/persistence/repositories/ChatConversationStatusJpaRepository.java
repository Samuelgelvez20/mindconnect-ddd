package com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.entity.ChatConversationStatusJpaEntity;

public interface ChatConversationStatusJpaRepository extends JpaRepository<ChatConversationStatusJpaEntity, UUID> {

    Optional<ChatConversationStatusJpaEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}