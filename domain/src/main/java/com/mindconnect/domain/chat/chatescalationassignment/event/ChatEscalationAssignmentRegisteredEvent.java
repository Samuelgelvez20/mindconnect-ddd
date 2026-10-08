package com.mindconnect.domain.chat.chatescalationassignment.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;

public record ChatEscalationAssignmentRegisteredEvent(ChatEscalationAssignmentId id, Instant occurredOn) implements DomainEvent {

    public ChatEscalationAssignmentRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}