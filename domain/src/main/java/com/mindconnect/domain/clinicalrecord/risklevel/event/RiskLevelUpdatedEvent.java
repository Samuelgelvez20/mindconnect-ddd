package com.mindconnect.domain.clinicalrecord.risklevel.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

public record RiskLevelUpdatedEvent(
        RiskLevelId id,
        String code,
        String name,
        boolean active,
        int severity,
        Instant occurredOn
) implements DomainEvent {

    public RiskLevelUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}