package com.mindconnect.domain.contact.emailcontact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;

public record EmailContactRegisteredEvent(EmailContactId id, Instant occurredOn) implements DomainEvent {

    public EmailContactRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}