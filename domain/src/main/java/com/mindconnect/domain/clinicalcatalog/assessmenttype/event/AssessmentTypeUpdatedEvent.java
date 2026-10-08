package com.mindconnect.domain.clinicalcatalog.assessmenttype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;

public record AssessmentTypeUpdatedEvent(AssessmentTypeId id, String code, String name, String description, boolean active, Instant occurredOn) implements DomainEvent {

    public AssessmentTypeUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}