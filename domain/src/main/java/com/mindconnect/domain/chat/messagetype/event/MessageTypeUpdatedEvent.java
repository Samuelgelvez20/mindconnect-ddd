package com.mindconnect.domain.chat.messagetype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;

public record MessageTypeUpdatedEvent(MessageTypeId id, String name, Instant occurredOn) implements DomainEvent {

    public MessageTypeUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}