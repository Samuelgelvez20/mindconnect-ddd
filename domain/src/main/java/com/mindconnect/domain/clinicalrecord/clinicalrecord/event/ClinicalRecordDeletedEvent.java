package com.mindconnect.domain.clinicalrecord.clinicalrecord.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;

public record ClinicalRecordDeletedEvent(ClinicalRecordId id, Instant occurredOn) implements DomainEvent {

    public ClinicalRecordDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}