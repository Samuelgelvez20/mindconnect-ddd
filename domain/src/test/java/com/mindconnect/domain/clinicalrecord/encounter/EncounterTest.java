package com.mindconnect.domain.clinicalrecord.encounter;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.encounter.event.EncounterDeletedEvent;
import com.mindconnect.domain.clinicalrecord.encounter.event.EncounterRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.encounter.event.EncounterUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.encounter.exception.InvalidEncounterException;
import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncounterTest {

    @Test
    void register_ValidData_CreatesEncounterWithEvents() {
        ClinicalRecordId clinicalRecordId = ClinicalRecordId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        EncounterTypeId encounterTypeId = EncounterTypeId.generate();
        Instant startedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        EncounterModalityId modalityId = EncounterModalityId.generate();
        EncounterStatusId statusId = EncounterStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Encounter encounter = Encounter.register(
                clinicalRecordId, professionalId, encounterTypeId, startedAt, modalityId, statusId, createdBy);

        assertNotNull(encounter.id());
        assertEquals(clinicalRecordId, encounter.clinicalRecordId());
        assertEquals(professionalId, encounter.professionalId());
        assertEquals(encounterTypeId, encounter.encounterTypeId());
        assertEquals(startedAt, encounter.startedAt());
        assertNull(encounter.endedAt());
        assertNull(encounter.reasonForVisit());
        assertNull(encounter.currentCondition());
        assertEquals(modalityId, encounter.modalityId());
        assertEquals(statusId, encounter.statusId());
        assertEquals(createdBy, encounter.createdBy());
        assertNull(encounter.updatedBy());
        assertNotNull(encounter.createdAt());
        assertNotNull(encounter.updatedAt());

        var events = encounter.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof EncounterRegisteredEvent);
    }

    @Test
    void register_NullClinicalRecordId_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(null, ProfessionalId.generate(), EncounterTypeId.generate(),
                        Instant.now(), EncounterModalityId.generate(), EncounterStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_NullProfessionalId_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(ClinicalRecordId.generate(), null, EncounterTypeId.generate(),
                        Instant.now(), EncounterModalityId.generate(), EncounterStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_NullEncounterTypeId_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), null,
                        Instant.now(), EncounterModalityId.generate(), EncounterStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_NullStartedAt_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                        null, EncounterModalityId.generate(), EncounterStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_NullModalityId_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                        Instant.now(), null, EncounterStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_NullStatusId_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                        Instant.now(), EncounterModalityId.generate(), null, ProfessionalId.generate()));
    }

    @Test
    void register_NullCreatedBy_ThrowsException() {
        assertThrows(InvalidEncounterException.class,
                () -> Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                        Instant.now(), EncounterModalityId.generate(), EncounterStatusId.generate(), null));
    }

    @Test
    void restore_CreatesEncounterWithoutEvents() {
        EncounterId id = EncounterId.generate();
        ClinicalRecordId clinicalRecordId = ClinicalRecordId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        EncounterTypeId encounterTypeId = EncounterTypeId.generate();
        Instant startedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Instant endedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        EncounterModalityId modalityId = EncounterModalityId.generate();
        EncounterStatusId statusId = EncounterStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        ProfessionalId updatedBy = ProfessionalId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        Encounter encounter = Encounter.restore(
                id, clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt, "reason", "condition",
                modalityId, statusId, createdBy, updatedBy, now, now);

        assertEquals(id, encounter.id());
        assertEquals(clinicalRecordId, encounter.clinicalRecordId());
        assertEquals(professionalId, encounter.professionalId());
        assertEquals(encounterTypeId, encounter.encounterTypeId());
        assertEquals(startedAt, encounter.startedAt());
        assertEquals(endedAt, encounter.endedAt());
        assertEquals("reason", encounter.reasonForVisit());
        assertEquals("condition", encounter.currentCondition());
        assertEquals(modalityId, encounter.modalityId());
        assertEquals(statusId, encounter.statusId());
        assertEquals(createdBy, encounter.createdBy());
        assertEquals(updatedBy, encounter.updatedBy());
        assertEquals(now, encounter.createdAt());
        assertEquals(now, encounter.updatedAt());
        assertTrue(encounter.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        ClinicalRecordId clinicalRecordId = ClinicalRecordId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        EncounterTypeId encounterTypeId = EncounterTypeId.generate();
        Instant startedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        EncounterModalityId modalityId = EncounterModalityId.generate();
        EncounterStatusId statusId = EncounterStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Encounter encounter = Encounter.register(
                clinicalRecordId, professionalId, encounterTypeId, startedAt, modalityId, statusId, createdBy);
        int initialEventCount = encounter.domainEvents().size();

        Instant endedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        String reason = "Updated reason";
        String condition = "Updated condition";
        EncounterModalityId newModalityId = EncounterModalityId.generate();
        EncounterStatusId newStatusId = EncounterStatusId.generate();
        ProfessionalId updatedBy = ProfessionalId.generate();

        encounter.update(clinicalRecordId, professionalId, encounterTypeId, startedAt, endedAt, reason, condition, newModalityId, newStatusId, updatedBy);

        assertEquals(endedAt, encounter.endedAt());
        assertEquals(reason, encounter.reasonForVisit());
        assertEquals(condition, encounter.currentCondition());
        assertEquals(newModalityId, encounter.modalityId());
        assertEquals(newStatusId, encounter.statusId());
        assertEquals(updatedBy, encounter.updatedBy());
        assertNotNull(encounter.updatedAt());

        var events = encounter.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterUpdatedEvent);
    }

    @Test
    void update_WithNullReasonAndCondition_UpdatesToNull() {
        ClinicalRecordId clinicalRecordId = ClinicalRecordId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        EncounterTypeId encounterTypeId = EncounterTypeId.generate();
        Instant startedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        EncounterModalityId modalityId = EncounterModalityId.generate();
        EncounterStatusId statusId = EncounterStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Encounter encounter = Encounter.register(
                clinicalRecordId, professionalId, encounterTypeId, startedAt, modalityId, statusId, createdBy);

        encounter.update(clinicalRecordId, professionalId, encounterTypeId, startedAt, null, null, null, modalityId, statusId, ProfessionalId.generate());

        assertNull(encounter.reasonForVisit());
        assertNull(encounter.currentCondition());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        Encounter encounter = Encounter.register(
                ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                Instant.now(), EncounterModalityId.generate(), EncounterStatusId.generate(), ProfessionalId.generate());
        int initialEventCount = encounter.domainEvents().size();

        encounter.delete();

        var events = encounter.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterDeletedEvent);
    }
}