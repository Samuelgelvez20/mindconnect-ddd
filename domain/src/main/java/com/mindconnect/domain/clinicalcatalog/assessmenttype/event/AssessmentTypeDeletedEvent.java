package com.mindconnect.domain.clinicalcatalog.assessmenttype.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;

public record AssessmentTypeDeletedEvent(AssessmentTypeId id, Instant occurredOn) implements DomainEvent {

    public AssessmentTypeDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}