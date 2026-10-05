package com.mindconnect.application.clinicalrecord.clinicalrecord.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;

public record UpdateClinicalRecordCommand(
        ClinicalRecordId id,
        PatientId patientId,
        String recordNumber,
        Instant openedAt,
        Instant closedAt,
        ClinicalRecordStatusId statusId) {

    public UpdateClinicalRecordCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(patientId, "patientId must not be null");
        Objects.requireNonNull(recordNumber, "recordNumber must not be null");
        Objects.requireNonNull(statusId, "statusId must not be null");
    }
}