package com.mindconnect.domain.contact.emailcontact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;

public record EmailContactUpdatedEvent(
        EmailContactId id,
        String email,
        Instant occurredOn
) implements DomainEvent {

    public EmailContactUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}