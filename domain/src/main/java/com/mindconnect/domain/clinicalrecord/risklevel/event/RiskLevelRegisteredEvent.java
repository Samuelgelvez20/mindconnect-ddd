package com.mindconnect.domain.clinicalrecord.risklevel.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

public record RiskLevelRegisteredEvent(RiskLevelId id, Instant occurredOn) implements DomainEvent {

    public RiskLevelRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}