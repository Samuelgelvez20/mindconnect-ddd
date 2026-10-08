package com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.entity.ChatAiRunJpaEntity;

@Repository
public interface ChatAiRunJpaRepository extends JpaRepository<ChatAiRunJpaEntity, UUID> {
}