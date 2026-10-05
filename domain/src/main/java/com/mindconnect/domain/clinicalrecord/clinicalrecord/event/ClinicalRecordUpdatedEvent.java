package com.mindconnect.domain.clinicalrecord.clinicalrecord.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ClinicalRecordUpdatedEvent(
        ClinicalRecordId id,
        PatientId patientId,
        String recordNumber,
        ClinicalRecordStatusId statusId,
        Instant occurredOn
) implements DomainEvent {

    public ClinicalRecordUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(patientId, "patientId must not be null");
        Objects.requireNonNull(recordNumber, "recordNumber must not be null");
        Objects.requireNonNull(statusId, "statusId must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}