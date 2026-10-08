package com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.entity.ChatConversationAiSettingsJpaEntity;

@Repository
public interface ChatConversationAiSettingsJpaRepository extends JpaRepository<ChatConversationAiSettingsJpaEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ChatConversationAiSettingsJpaEntity e WHERE e.conversationId = :conversationId")
    boolean existsByConversationId(@Param("conversationId") UUID conversationId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ChatConversationAiSettingsJpaEntity e WHERE e.conversationId = :conversationId AND e.id <> :id")
    boolean existsByConversationIdAndIdNot(@Param("conversationId") UUID conversationId, @Param("id") UUID id);

    Optional<ChatConversationAiSettingsJpaEntity> findByConversationId(UUID conversationId);
}