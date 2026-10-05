package com.mindconnect.domain.clinicalrecord.mentalstatusexam.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;

public record MentalStatusExamDeletedEvent(MentalStatusExamId id, Instant occurredOn) implements DomainEvent {

    public MentalStatusExamDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}