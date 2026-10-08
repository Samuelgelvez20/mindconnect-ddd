package com.mindconnect.domain.contact.phonecontact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

public record PhoneContactRegisteredEvent(PhoneContactId id, Instant occurredOn) implements DomainEvent {

    public PhoneContactRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}