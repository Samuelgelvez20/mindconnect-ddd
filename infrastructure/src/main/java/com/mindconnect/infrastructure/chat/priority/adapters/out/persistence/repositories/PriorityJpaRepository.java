package com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.entity.PriorityJpaEntity;

public interface PriorityJpaRepository extends JpaRepository<PriorityJpaEntity, UUID> {

    Optional<PriorityJpaEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}