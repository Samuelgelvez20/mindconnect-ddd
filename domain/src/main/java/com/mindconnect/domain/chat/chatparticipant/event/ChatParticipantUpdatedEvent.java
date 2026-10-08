package com.mindconnect.domain.chat.chatparticipant.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ChatParticipantUpdatedEvent(
        ChatParticipantId id,
        ChatConversationId conversationId,
        SenderTypeId participantTypeId,
        PatientId patientId,
        ProfessionalId professionalId,
        Instant occurredOn) implements DomainEvent {

    public ChatParticipantUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}