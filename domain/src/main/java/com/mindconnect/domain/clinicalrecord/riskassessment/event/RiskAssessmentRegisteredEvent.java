package com.mindconnect.domain.clinicalrecord.riskassessment.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;

public record RiskAssessmentRegisteredEvent(RiskAssessmentId id, Instant occurredOn) implements DomainEvent {

    public RiskAssessmentRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}