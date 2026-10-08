package com.mindconnect.domain.professional.professional.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ProfessionalDeletedEvent(ProfessionalId id, Instant occurredOn) implements DomainEvent {

    public ProfessionalDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}