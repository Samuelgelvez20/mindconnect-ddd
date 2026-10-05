package com.mindconnect.domain.contact.patientcontact.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;

public record PatientContactRegisteredEvent(PatientContactId id, Instant occurredOn) implements DomainEvent {

    public PatientContactRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}