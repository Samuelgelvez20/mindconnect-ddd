package com.mindconnect.domain.chat.chatescalationstatushistory.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;

public record ChatEscalationStatusHistoryRegisteredEvent(ChatEscalationStatusHistoryId id, Instant occurredOn) implements DomainEvent {

    public ChatEscalationStatusHistoryRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}