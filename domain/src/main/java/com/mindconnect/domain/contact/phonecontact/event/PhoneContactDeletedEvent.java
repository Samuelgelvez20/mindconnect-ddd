package com.mindconnect.domain.contact.phonecontact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

public record PhoneContactDeletedEvent(PhoneContactId id, Instant occurredOn) implements DomainEvent {

    public PhoneContactDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}