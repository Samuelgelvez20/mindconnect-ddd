package com.mindconnect.domain.professional.study.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

public record StudyRegisteredEvent(StudyId id, Instant occurredOn) implements DomainEvent {

    public StudyRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}