package com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.entity.ChatAiRunErrorJpaEntity;

@Repository
public interface ChatAiRunErrorJpaRepository extends JpaRepository<ChatAiRunErrorJpaEntity, UUID> {

    List<ChatAiRunErrorJpaEntity> findByAiRunId(UUID aiRunId);
}