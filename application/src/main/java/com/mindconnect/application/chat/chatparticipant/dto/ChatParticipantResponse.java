package com.mindconnect.application.chat.chatparticipant.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatparticipant.model.aggregate.ChatParticipant;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ChatParticipantResponse(
        UUID id,
        UUID conversationId,
        UUID participantTypeId,
        UUID patientId,
        UUID professionalId,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatParticipantResponse from(ChatParticipant participant) {
        return new ChatParticipantResponse(
                participant.id().value(),
                participant.conversationId().value(),
                participant.participantTypeId().value(),
                participant.patientId() != null ? participant.patientId().value() : null,
                participant.professionalId() != null ? participant.professionalId().value() : null,
                participant.createdAt(),
                participant.updatedAt());
    }
}