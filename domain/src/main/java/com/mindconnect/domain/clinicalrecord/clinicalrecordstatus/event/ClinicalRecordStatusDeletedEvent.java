package com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;

public record ClinicalRecordStatusDeletedEvent(ClinicalRecordStatusId id, Instant occurredOn) implements DomainEvent {

    public ClinicalRecordStatusDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}