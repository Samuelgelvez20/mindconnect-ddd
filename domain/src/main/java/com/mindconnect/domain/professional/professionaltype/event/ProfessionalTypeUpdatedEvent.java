package com.mindconnect.domain.professional.professionaltype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public record ProfessionalTypeUpdatedEvent(
        ProfessionalTypeId id,
        String name,
        Instant occurredOn
) implements DomainEvent {

    public ProfessionalTypeUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}