package com.mindconnect.domain.clinicalrecord.clinicalnote.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;

public record ClinicalNoteDeletedEvent(ClinicalNoteId id, Instant occurredOn) implements DomainEvent {

    public ClinicalNoteDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}