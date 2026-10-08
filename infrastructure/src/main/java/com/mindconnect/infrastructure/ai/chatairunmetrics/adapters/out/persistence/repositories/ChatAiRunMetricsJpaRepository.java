package com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.out.persistence.entity.ChatAiRunMetricsJpaEntity;

@Repository
public interface ChatAiRunMetricsJpaRepository extends JpaRepository<ChatAiRunMetricsJpaEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ChatAiRunMetricsJpaEntity e WHERE e.aiRunId = :aiRunId")
    boolean existsByAiRunId(@Param("aiRunId") UUID aiRunId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ChatAiRunMetricsJpaEntity e WHERE e.aiRunId = :aiRunId AND e.id <> :id")
    boolean existsByAiRunIdAndIdNot(@Param("aiRunId") UUID aiRunId, @Param("id") UUID id);

    Optional<ChatAiRunMetricsJpaEntity> findByAiRunId(UUID aiRunId);
}