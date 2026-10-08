package com.mindconnect.domain.chat.chatescalation.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;

public record ChatEscalationDeletedEvent(ChatEscalationId id, Instant occurredOn) implements DomainEvent {

    public ChatEscalationDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}