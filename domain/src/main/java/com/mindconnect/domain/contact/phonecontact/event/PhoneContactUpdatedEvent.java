package com.mindconnect.domain.contact.phonecontact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

public record PhoneContactUpdatedEvent(
        PhoneContactId id,
        String phone,
        Instant occurredOn
) implements DomainEvent {

    public PhoneContactUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(phone, "phone must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}