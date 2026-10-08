package com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;

public record DiagnosticSystemUpdatedEvent(DiagnosticSystemId id, String code, String name, String version, boolean active, Instant occurredOn) implements DomainEvent {

    public DiagnosticSystemUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}