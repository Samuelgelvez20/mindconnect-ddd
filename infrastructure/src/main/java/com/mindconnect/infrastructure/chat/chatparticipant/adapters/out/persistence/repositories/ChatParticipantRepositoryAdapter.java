package com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatparticipant.model.aggregate.ChatParticipant;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.entity.ChatParticipantJpaEntity;
import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.mappers.ChatParticipantPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatParticipantRepositoryAdapter implements ChatParticipantRepository {

    private final ChatParticipantJpaRepository jpaRepository;
    private final ChatParticipantPersistenceMapper mapper;

    public ChatParticipantRepositoryAdapter(ChatParticipantJpaRepository jpaRepository, ChatParticipantPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatParticipant save(ChatParticipant participant) {
        ChatParticipantJpaEntity entity = mapper.toJpa(participant);
        ChatParticipantJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatParticipant> findById(ChatParticipantId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatParticipant> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatParticipant participant) {
        jpaRepository.deleteById(participant.id().value());
    }

    public List<ChatParticipant> findByConversationId(ChatConversationId conversationId) {
        return jpaRepository.findByConversationId(conversationId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatParticipant> findByParticipantTypeId(SenderTypeId participantTypeId) {
        return jpaRepository.findByParticipantTypeId(participantTypeId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatParticipant> findByPatientId(PatientId patientId) {
        return jpaRepository.findByPatientId(patientId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatParticipant> findByProfessionalId(ProfessionalId professionalId) {
        return jpaRepository.findByProfessionalId(professionalId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}