package com.mindconnect.domain.treatment.treatmentplan.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;

public record TreatmentPlanUpdatedEvent(
        TreatmentPlanId id,
        EncounterId encounterId,
        ProfessionalId professionalId,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        TreatmentStatusId treatmentStatusId,
        Instant occurredOn) implements DomainEvent {

    public TreatmentPlanUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}