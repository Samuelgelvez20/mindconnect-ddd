package com.mindconnect.domain.clinicalrecord.encounter.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

public record EncounterUpdatedEvent(
        EncounterId id,
        ClinicalRecordId clinicalRecordId,
        ProfessionalId professionalId,
        EncounterTypeId encounterTypeId,
        Instant startedAt,
        EncounterModalityId modalityId,
        EncounterStatusId statusId,
        Instant occurredOn
) implements DomainEvent {

    public EncounterUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(clinicalRecordId, "clinicalRecordId must not be null");
        Objects.requireNonNull(professionalId, "professionalId must not be null");
        Objects.requireNonNull(encounterTypeId, "encounterTypeId must not be null");
        Objects.requireNonNull(startedAt, "startedAt must not be null");
        Objects.requireNonNull(modalityId, "modalityId must not be null");
        Objects.requireNonNull(statusId, "statusId must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}