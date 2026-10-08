package com.mindconnect.domain.contact.contact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public record ContactDeletedEvent(ContactId id, Instant occurredOn) implements DomainEvent {

    public ContactDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}