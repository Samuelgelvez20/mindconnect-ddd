package com.mindconnect.domain.contact.contact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public record ContactUpdatedEvent(
        ContactId id,
        String fullName,
        String email,
        Instant occurredOn
) implements DomainEvent {

    public ContactUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}