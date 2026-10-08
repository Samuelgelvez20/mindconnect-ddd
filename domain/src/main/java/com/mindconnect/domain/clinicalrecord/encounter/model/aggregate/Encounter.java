package com.mindconnect.domain.clinicalrecord.encounter.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.clinicalrecord.encounter.event.EncounterDeletedEvent;
import com.mindconnect.domain.clinicalrecord.encounter.event.EncounterRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.encounter.event.EncounterUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.encounter.exception.InvalidEncounterException;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class Encounter extends AggregateRoot {

    private final EncounterId id;
    private ClinicalRecordId clinicalRecordId;
    private ProfessionalId professionalId;
    private EncounterTypeId encounterTypeId;
    private Instant startedAt;
    private Instant endedAt;
    private String reasonForVisit;
    private String currentCondition;
    private EncounterModalityId modalityId;
    private EncounterStatusId statusId;
    private ProfessionalId createdBy;
    private ProfessionalId updatedBy;
    private final Instant createdAt;
    private Instant updatedAt;

    private Encounter(
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
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.clinicalRecordId = clinicalRecordId;
        this.professionalId = professionalId;
        this.encounterTypeId = encounterTypeId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.reasonForVisit = reasonForVisit;
        this.currentCondition = currentCondition;
        this.modalityId = modalityId;
        this.statusId = statusId;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Encounter register(
            ClinicalRecordId clinicalRecordId,
            ProfessionalId professionalId,
            EncounterTypeId encounterTypeId,
            Instant startedAt,
            EncounterModalityId modalityId,
            EncounterStatusId statusId,
            ProfessionalId createdBy) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        EncounterId id = EncounterId.generate();

        if (clinicalRecordId == null) {
            throw new InvalidEncounterException("clinicalRecordId must not be null");
        }
        if (professionalId == null) {
            throw new InvalidEncounterException("professionalId must not be null");
        }
        if (encounterTypeId == null) {
            throw new InvalidEncounterException("encounterTypeId must not be null");
        }
        if (startedAt == null) {
            throw new InvalidEncounterException("startedAt must not be null");
        }
        if (modalityId == null) {
            throw new InvalidEncounterException("modalityId must not be null");
        }
        if (statusId == null) {
            throw new InvalidEncounterException("statusId must not be null");
        }
        if (createdBy == null) {
            throw new InvalidEncounterException("createdBy must not be null");
        }

        Encounter encounter = new Encounter(
                id,
                clinicalRecordId,
                professionalId,
                encounterTypeId,
                startedAt,
                null,
                null,
                null,
                modalityId,
                statusId,
                createdBy,
                null,
                now,
                now);

        encounter.recordEvent(new EncounterRegisteredEvent(id, now));
        return encounter;
    }

    public static Encounter restore(
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
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            Instant createdAt,
            Instant updatedAt) {

        return new Encounter(id, clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt,
                reasonForVisit, currentCondition, modalityId, statusId, createdBy, updatedBy, createdAt, updatedAt);
    }

    public void update(
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

        this.clinicalRecordId = Objects.requireNonNull(clinicalRecordId, "clinicalRecordId must not be null");
        this.professionalId = Objects.requireNonNull(professionalId, "professionalId must not be null");
        this.encounterTypeId = Objects.requireNonNull(encounterTypeId, "encounterTypeId must not be null");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt must not be null");
        this.endedAt = endedAt;
        this.reasonForVisit = reasonForVisit != null && reasonForVisit.isBlank() ? null : reasonForVisit;
        this.currentCondition = currentCondition != null && currentCondition.isBlank() ? null : currentCondition;
        this.modalityId = Objects.requireNonNull(modalityId, "modalityId must not be null");
        this.statusId = Objects.requireNonNull(statusId, "statusId must not be null");
        this.updatedBy = Objects.requireNonNull(updatedBy, "updatedBy must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new EncounterUpdatedEvent(
                this.id, this.clinicalRecordId, this.professionalId, this.encounterTypeId,
                this.startedAt, this.modalityId, this.statusId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new EncounterDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public EncounterId id() {
        return id;
    }

    public ClinicalRecordId clinicalRecordId() {
        return clinicalRecordId;
    }

    public ProfessionalId professionalId() {
        return professionalId;
    }

    public EncounterTypeId encounterTypeId() {
        return encounterTypeId;
    }

    public Instant startedAt() {
        return startedAt;
    }

    public Instant endedAt() {
        return endedAt;
    }

    public String reasonForVisit() {
        return reasonForVisit;
    }

    public String currentCondition() {
        return currentCondition;
    }

    public EncounterModalityId modalityId() {
        return modalityId;
    }

    public EncounterStatusId statusId() {
        return statusId;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public ProfessionalId updatedBy() {
        return updatedBy;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}