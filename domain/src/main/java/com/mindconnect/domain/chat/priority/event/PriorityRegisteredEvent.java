package com.mindconnect.domain.chat.priority.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public record PriorityRegisteredEvent(PriorityId id, Instant occurredOn) implements DomainEvent {

    public PriorityRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}