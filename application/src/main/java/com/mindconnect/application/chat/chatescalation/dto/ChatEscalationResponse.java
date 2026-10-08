package com.mindconnect.application.chat.chatescalation.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatescalation.model.aggregate.ChatEscalation;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record ChatEscalationResponse(
        UUID id,
        UUID conversationId,
        UUID statusId,
        boolean fromAi,
        String reason,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatEscalationResponse from(ChatEscalation escalation) {
        return new ChatEscalationResponse(
                escalation.id().value(),
                escalation.conversationId().value(),
                escalation.statusId().value(),
                escalation.fromAi(),
                escalation.reason(),
                escalation.createdAt(),
                escalation.updatedAt());
    }
}