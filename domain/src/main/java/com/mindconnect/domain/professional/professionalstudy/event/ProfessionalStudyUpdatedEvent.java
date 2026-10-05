package com.mindconnect.domain.professional.professionalstudy.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;

public record ProfessionalStudyUpdatedEvent(
        ProfessionalStudyId id,
        String title,
        String university,
        Instant occurredOn
) implements DomainEvent {

    public ProfessionalStudyUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(university, "university must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}