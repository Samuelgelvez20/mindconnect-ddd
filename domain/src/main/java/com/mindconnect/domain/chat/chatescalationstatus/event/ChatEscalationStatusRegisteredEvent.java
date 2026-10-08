package com.mindconnect.domain.chat.chatescalationstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record ChatEscalationStatusRegisteredEvent(ChatEscalationStatusId id, Instant occurredOn) implements DomainEvent {

    public ChatEscalationStatusRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}