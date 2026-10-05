package com.mindconnect.domain.professional.professionaltype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public record ProfessionalTypeDeletedEvent(ProfessionalTypeId id, Instant occurredOn) implements DomainEvent {

    public ProfessionalTypeDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}