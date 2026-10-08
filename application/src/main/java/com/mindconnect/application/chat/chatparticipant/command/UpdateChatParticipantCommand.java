package com.mindconnect.application.chat.chatparticipant.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record UpdateChatParticipantCommand(
        ChatParticipantId id,
        ChatConversationId conversationId,
        SenderTypeId participantTypeId,
        PatientId patientId,
        ProfessionalId professionalId) {

    public UpdateChatParticipantCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(conversationId, "conversationId must not be null");
        Objects.requireNonNull(participantTypeId, "participantTypeId must not be null");
    }
}