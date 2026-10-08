package com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.entity.AiModelJpaEntity;

@Repository
public interface AiModelJpaRepository extends JpaRepository<AiModelJpaEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM AiModelJpaEntity e WHERE e.modelKey = :modelKey")
    boolean existsByModelKey(@Param("modelKey") String modelKey);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM AiModelJpaEntity e WHERE e.modelKey = :modelKey AND e.id <> :id")
    boolean existsByModelKeyAndIdNot(@Param("modelKey") String modelKey, @Param("id") UUID id);
}