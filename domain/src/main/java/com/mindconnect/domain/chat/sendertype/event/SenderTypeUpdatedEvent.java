package com.mindconnect.domain.chat.sendertype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;

public record SenderTypeUpdatedEvent(SenderTypeId id, String name, Instant occurredOn) implements DomainEvent {

    public SenderTypeUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}