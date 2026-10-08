package com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.entity.MessageTypeJpaEntity;

public interface MessageTypeJpaRepository extends JpaRepository<MessageTypeJpaEntity, UUID> {

    Optional<MessageTypeJpaEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}