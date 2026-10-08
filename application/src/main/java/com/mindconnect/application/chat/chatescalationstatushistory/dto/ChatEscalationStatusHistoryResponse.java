package com.mindconnect.application.chat.chatescalationstatushistory.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record ChatEscalationStatusHistoryResponse(
        UUID id,
        UUID escalationId,
        UUID escalationStatusId,
        Instant changedAt,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatEscalationStatusHistoryResponse from(ChatEscalationStatusHistory history) {
        return new ChatEscalationStatusHistoryResponse(
                history.id().value(),
                history.escalationId().value(),
                history.escalationStatusId().value(),
                history.changedAt(),
                history.createdAt(),
                history.updatedAt());
    }
}