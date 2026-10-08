package com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.repositories;

import com.mindconnect.domain.chat.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.entity.ChatEscalationAssignmentJpaEntity;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.mappers.ChatEscalationAssignmentPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatEscalationAssignmentRepositoryAdapter implements ChatEscalationAssignmentRepository {

    private final ChatEscalationAssignmentJpaRepository jpaRepository;
    private final ChatEscalationAssignmentPersistenceMapper mapper;

    public ChatEscalationAssignmentRepositoryAdapter(ChatEscalationAssignmentJpaRepository jpaRepository, ChatEscalationAssignmentPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ChatEscalationAssignment save(ChatEscalationAssignment assignment) {
        ChatEscalationAssignmentJpaEntity entity = mapper.toJpa(assignment);
        ChatEscalationAssignmentJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ChatEscalationAssignment> findById(ChatEscalationAssignmentId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<ChatEscalationAssignment> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ChatEscalationAssignment assignment) {
        jpaRepository.deleteById(assignment.id().value());
    }

    public List<ChatEscalationAssignment> findByEscalationId(ChatEscalationId escalationId) {
        return jpaRepository.findByEscalationId(escalationId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ChatEscalationAssignment> findByProfessionalId(ProfessionalId professionalId) {
        return jpaRepository.findByProfessionalId(professionalId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public boolean existsByEscalationIdAndProfessionalId(ChatEscalationId escalationId, ProfessionalId professionalId) {
        return jpaRepository.existsByEscalationIdAndProfessionalId(escalationId.value(), professionalId.value());
    }
}