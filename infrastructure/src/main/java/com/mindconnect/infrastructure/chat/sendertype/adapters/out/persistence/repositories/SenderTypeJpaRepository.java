package com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.entity.SenderTypeJpaEntity;

public interface SenderTypeJpaRepository extends JpaRepository<SenderTypeJpaEntity, UUID> {

    Optional<SenderTypeJpaEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}