package com.mindconnect.domain.patient.patientallergy.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;

public record PatientAllergyDeletedEvent(PatientAllergyId id, Instant occurredOn) implements DomainEvent {

    public PatientAllergyDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}