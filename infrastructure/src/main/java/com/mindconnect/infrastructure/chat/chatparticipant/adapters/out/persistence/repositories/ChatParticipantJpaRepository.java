package com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.entity.ChatParticipantJpaEntity;

public interface ChatParticipantJpaRepository extends JpaRepository<ChatParticipantJpaEntity, UUID> {

    List<ChatParticipantJpaEntity> findByConversationId(UUID conversationId);

    List<ChatParticipantJpaEntity> findByParticipantTypeId(UUID participantTypeId);

    List<ChatParticipantJpaEntity> findByPatientId(UUID patientId);

    List<ChatParticipantJpaEntity> findByProfessionalId(UUID professionalId);
}