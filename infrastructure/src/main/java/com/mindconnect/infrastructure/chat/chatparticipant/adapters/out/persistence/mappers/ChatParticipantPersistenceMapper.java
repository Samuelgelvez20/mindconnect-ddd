package com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.mappers;

import com.mindconnect.domain.chat.chatparticipant.model.aggregate.ChatParticipant;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.entity.ChatParticipantJpaEntity;

public class ChatParticipantPersistenceMapper {

    public ChatParticipantJpaEntity toJpa(ChatParticipant domain) {
        if (domain == null) {
            return null;
        }

        ChatParticipantJpaEntity jpa = new ChatParticipantJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setConversationId(domain.conversationId().value());
        jpa.setParticipantTypeId(domain.participantTypeId().value());
        jpa.setPatientId(domain.patientId() != null ? domain.patientId().value() : null);
        jpa.setProfessionalId(domain.professionalId() != null ? domain.professionalId().value() : null);
        jpa.setCreatedAt(domain.createdAt());
        jpa.setUpdatedAt(domain.updatedAt());
        return jpa;
    }

    public ChatParticipant toDomain(ChatParticipantJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return ChatParticipant.restore(
                new ChatParticipantId(jpa.getId()),
                new ChatConversationId(jpa.getConversationId()),
                new SenderTypeId(jpa.getParticipantTypeId()),
                jpa.getPatientId() != null ? new PatientId(jpa.getPatientId()) : null,
                jpa.getProfessionalId() != null ? new ProfessionalId(jpa.getProfessionalId()) : null,
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }
}