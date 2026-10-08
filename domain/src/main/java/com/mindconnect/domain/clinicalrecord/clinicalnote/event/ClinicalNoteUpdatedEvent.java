package com.mindconnect.domain.clinicalrecord.clinicalnote.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ClinicalNoteUpdatedEvent(
        ClinicalNoteId id,
        EncounterId encounterId,
        ProfessionalId professionalId,
        Instant occurredOn
) implements DomainEvent {

    public ClinicalNoteUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(encounterId, "encounterId must not be null");
        Objects.requireNonNull(professionalId, "professionalId must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}