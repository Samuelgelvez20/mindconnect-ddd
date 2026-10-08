package com.mindconnect.domain.professional.professionaltype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public record ProfessionalTypeRegisteredEvent(ProfessionalTypeId id, Instant occurredOn) implements DomainEvent {

    public ProfessionalTypeRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}