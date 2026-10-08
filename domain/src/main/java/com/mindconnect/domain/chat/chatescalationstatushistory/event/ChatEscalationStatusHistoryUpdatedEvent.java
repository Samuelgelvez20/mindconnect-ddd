package com.mindconnect.domain.chat.chatescalationstatushistory.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record ChatEscalationStatusHistoryUpdatedEvent(
        ChatEscalationStatusHistoryId id,
        ChatEscalationId escalationId,
        ChatEscalationStatusId escalationStatusId,
        Instant changedAt,
        Instant occurredOn) implements DomainEvent {

    public ChatEscalationStatusHistoryUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}