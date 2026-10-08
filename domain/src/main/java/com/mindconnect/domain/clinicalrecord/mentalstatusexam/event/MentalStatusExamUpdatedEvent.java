package com.mindconnect.domain.clinicalrecord.mentalstatusexam.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record MentalStatusExamUpdatedEvent(
        MentalStatusExamId id,
        EncounterId encounterId,
        ProfessionalId createdBy,
        Instant occurredOn
) implements DomainEvent {

    public MentalStatusExamUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(encounterId, "encounterId must not be null");
        Objects.requireNonNull(createdBy, "createdBy must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}