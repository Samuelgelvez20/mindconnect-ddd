package com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;

public record DiagnosticSystemRegisteredEvent(DiagnosticSystemId id, Instant occurredOn) implements DomainEvent {

    public DiagnosticSystemRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}