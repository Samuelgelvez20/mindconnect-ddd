package com.mindconnect.application.clinicalrecord.clinicalrecord.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record RegisterClinicalRecordCommand(
        PatientId patientId,
        String recordNumber,
        ClinicalRecordStatusId statusId,
        ProfessionalId createdBy) {

    public RegisterClinicalRecordCommand {
        Objects.requireNonNull(patientId, "patientId must not be null");
        Objects.requireNonNull(recordNumber, "recordNumber must not be null");
        Objects.requireNonNull(statusId, "statusId must not be null");
        Objects.requireNonNull(createdBy, "createdBy must not be null");
    }
}