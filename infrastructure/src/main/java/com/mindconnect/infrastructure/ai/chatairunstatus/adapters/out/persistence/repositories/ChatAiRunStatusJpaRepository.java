package com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.entity.ChatAiRunStatusJpaEntity;

@Repository
public interface ChatAiRunStatusJpaRepository extends JpaRepository<ChatAiRunStatusJpaEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ChatAiRunStatusJpaEntity e WHERE e.name = :name")
    boolean existsByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ChatAiRunStatusJpaEntity e WHERE e.name = :name AND e.id <> :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") UUID id);

    Optional<ChatAiRunStatusJpaEntity> findByName(String name);
}