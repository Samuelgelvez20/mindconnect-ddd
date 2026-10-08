package com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.entity.ChatEscalationAssignmentJpaEntity;

public class ChatEscalationAssignmentPersistenceMapper {

    public ChatEscalationAssignmentJpaEntity toJpa(ChatEscalationAssignment domain) {
        if (domain == null) {
            return null;
        }

        ChatEscalationAssignmentJpaEntity jpa = new ChatEscalationAssignmentJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setEscalationId(domain.escalationId().value());
        jpa.setProfessionalId(domain.professionalId().value());
        jpa.setAssignedAt(domain.assignedAt());
        return jpa;
    }

    public ChatEscalationAssignment toDomain(ChatEscalationAssignmentJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return ChatEscalationAssignment.restore(
                new ChatEscalationAssignmentId(jpa.getId()),
                new ChatEscalationId(jpa.getEscalationId()),
                new ProfessionalId(jpa.getProfessionalId()),
                jpa.getAssignedAt(),
                jpa.getAssignedAt());
    }
}