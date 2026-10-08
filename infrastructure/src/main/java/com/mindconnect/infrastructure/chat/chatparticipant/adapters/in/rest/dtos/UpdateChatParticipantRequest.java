package com.mindconnect.infrastructure.chat.chatparticipant.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateChatParticipantRequest(

        @NotNull(message = "conversationId is required")
        java.util.UUID conversationId,

        @NotNull(message = "participantTypeId is required")
        java.util.UUID participantTypeId,

        java.util.UUID patientId,

        java.util.UUID professionalId
) {
}