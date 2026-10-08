package com.mindconnect.application.clinicalrecord.encounter.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record UpdateEncounterCommand(
        EncounterId id,
        ClinicalRecordId clinicalRecordId,
        ProfessionalId professionalId,
        EncounterTypeId encounterTypeId,
        Instant startedAt,
        Instant endedAt,
        String reasonForVisit,
        String currentCondition,
        EncounterModalityId modalityId,
        EncounterStatusId statusId,
        ProfessionalId updatedBy) {

    public UpdateEncounterCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(clinicalRecordId, "clinicalRecordId must not be null");
        Objects.requireNonNull(professionalId, "professionalId must not be null");
        Objects.requireNonNull(encounterTypeId, "encounterTypeId must not be null");
        Objects.requireNonNull(startedAt, "startedAt must not be null");
        Objects.requireNonNull(modalityId, "modalityId must not be null");
        Objects.requireNonNull(statusId, "statusId must not be null");
        Objects.requireNonNull(updatedBy, "updatedBy must not be null");
    }
}