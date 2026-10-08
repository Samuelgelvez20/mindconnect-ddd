package com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.entity.ChatEscalationStatusHistoryJpaEntity;

public interface ChatEscalationStatusHistoryJpaRepository extends JpaRepository<ChatEscalationStatusHistoryJpaEntity, UUID> {

    List<ChatEscalationStatusHistoryJpaEntity> findByEscalationId(UUID escalationId);

    List<ChatEscalationStatusHistoryJpaEntity> findByEscalationStatusId(UUID escalationStatusId);
}