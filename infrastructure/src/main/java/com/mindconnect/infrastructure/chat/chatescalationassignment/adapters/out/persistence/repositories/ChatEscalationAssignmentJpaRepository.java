package com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.entity.ChatEscalationAssignmentJpaEntity;

public interface ChatEscalationAssignmentJpaRepository extends JpaRepository<ChatEscalationAssignmentJpaEntity, UUID> {

    List<ChatEscalationAssignmentJpaEntity> findByEscalationId(UUID escalationId);

    List<ChatEscalationAssignmentJpaEntity> findByProfessionalId(UUID professionalId);

    Optional<ChatEscalationAssignmentJpaEntity> findByEscalationIdAndProfessionalId(UUID escalationId, UUID professionalId);

    boolean existsByEscalationIdAndProfessionalId(UUID escalationId, UUID professionalId);
}