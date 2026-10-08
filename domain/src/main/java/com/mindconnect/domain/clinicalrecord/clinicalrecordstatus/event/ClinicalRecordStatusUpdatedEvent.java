package com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;

public record ClinicalRecordStatusUpdatedEvent(
        ClinicalRecordStatusId id,
        String code,
        String name,
        Instant occurredOn
) implements DomainEvent {

    public ClinicalRecordStatusUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}