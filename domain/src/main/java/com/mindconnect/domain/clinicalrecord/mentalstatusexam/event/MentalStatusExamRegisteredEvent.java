package com.mindconnect.domain.clinicalrecord.mentalstatusexam.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;

public record MentalStatusExamRegisteredEvent(MentalStatusExamId id, Instant occurredOn) implements DomainEvent {

    public MentalStatusExamRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}