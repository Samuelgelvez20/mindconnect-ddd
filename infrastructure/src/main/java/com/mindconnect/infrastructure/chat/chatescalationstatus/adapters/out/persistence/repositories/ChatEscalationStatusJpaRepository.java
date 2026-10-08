package com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.entity.ChatEscalationStatusJpaEntity;

public interface ChatEscalationStatusJpaRepository extends JpaRepository<ChatEscalationStatusJpaEntity, UUID> {

    Optional<ChatEscalationStatusJpaEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}