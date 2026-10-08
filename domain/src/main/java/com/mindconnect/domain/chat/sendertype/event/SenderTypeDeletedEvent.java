package com.mindconnect.domain.chat.sendertype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;

public record SenderTypeDeletedEvent(SenderTypeId id, Instant occurredOn) implements DomainEvent {

    public SenderTypeDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}