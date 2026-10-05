package com.mindconnect.domain.professional.professionalstudy.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;

public record ProfessionalStudyRegisteredEvent(ProfessionalStudyId id, Instant occurredOn) implements DomainEvent {

    public ProfessionalStudyRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}