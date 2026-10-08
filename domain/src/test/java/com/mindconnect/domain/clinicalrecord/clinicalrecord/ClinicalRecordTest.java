package com.mindconnect.domain.clinicalrecord.clinicalrecord;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.clinicalrecord.event.ClinicalRecordDeletedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.event.ClinicalRecordRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.event.ClinicalRecordUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.exception.InvalidClinicalRecordException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClinicalRecordTest {

    @Test
    void register_ValidData_CreatesRecordWithEvents() {
        PatientId patientId = PatientId.generate();
        String recordNumber = "CR-001";
        ClinicalRecordStatusId statusId = ClinicalRecordStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        ClinicalRecord record = ClinicalRecord.register(patientId, recordNumber, statusId, createdBy);

        assertNotNull(record.id());
        assertEquals(patientId, record.patientId());
        assertNotNull(record.creationDate());
        assertEquals(recordNumber, record.recordNumber());
        assertNull(record.openedAt());
        assertNull(record.closedAt());
        assertEquals(statusId, record.statusId());
        assertEquals(createdBy, record.createdBy());
        assertNotNull(record.createdAt());
        assertNotNull(record.updatedAt());

        var events = record.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ClinicalRecordRegisteredEvent);
    }

    @Test
    void register_NullPatientId_ThrowsException() {
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(null, "CR-001", ClinicalRecordStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_NullStatusId_ThrowsException() {
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(PatientId.generate(), "CR-001", null, ProfessionalId.generate()));
    }

    @Test
    void register_NullCreatedBy_ThrowsException() {
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(PatientId.generate(), "CR-001", ClinicalRecordStatusId.generate(), null));
    }

    @Test
    void register_RecordNumberBlank_ThrowsException() {
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(PatientId.generate(), " ", ClinicalRecordStatusId.generate(), ProfessionalId.generate()));
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(PatientId.generate(), "", ClinicalRecordStatusId.generate(), ProfessionalId.generate()));
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(PatientId.generate(), null, ClinicalRecordStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void register_RecordNumberTooLong_ThrowsException() {
        String longRecordNumber = "a".repeat(51);
        assertThrows(InvalidClinicalRecordException.class,
                () -> ClinicalRecord.register(PatientId.generate(), longRecordNumber, ClinicalRecordStatusId.generate(), ProfessionalId.generate()));
    }

    @Test
    void restore_CreatesRecordWithoutEvents() {
        ClinicalRecordId id = ClinicalRecordId.generate();
        PatientId patientId = PatientId.generate();
        ClinicalRecordStatusId statusId = ClinicalRecordStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ClinicalRecord record = ClinicalRecord.restore(id, patientId, now, "CR-001", now, now, statusId, createdBy, now, now);

        assertEquals(id, record.id());
        assertEquals(patientId, record.patientId());
        assertEquals(now, record.creationDate());
        assertEquals("CR-001", record.recordNumber());
        assertEquals(now, record.openedAt());
        assertEquals(now, record.closedAt());
        assertEquals(statusId, record.statusId());
        assertEquals(createdBy, record.createdBy());
        assertEquals(now, record.createdAt());
        assertEquals(now, record.updatedAt());
        assertTrue(record.domainEvents().isEmpty());
    }

    @Test
    void restore_WithNullOpenedAndClosedAt_CreatesRecordWithNulls() {
        ClinicalRecordId id = ClinicalRecordId.generate();
        PatientId patientId = PatientId.generate();
        ClinicalRecordStatusId statusId = ClinicalRecordStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ClinicalRecord record = ClinicalRecord.restore(id, patientId, now, "CR-001", null, null, statusId, createdBy, now, now);

        assertNull(record.openedAt());
        assertNull(record.closedAt());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        PatientId patientId = PatientId.generate();
        String recordNumber = "CR-001";
        ClinicalRecordStatusId statusId = ClinicalRecordStatusId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        ClinicalRecord record = ClinicalRecord.register(patientId, recordNumber, statusId, createdBy);
        int initialEventCount = record.domainEvents().size();

        PatientId newPatientId = PatientId.generate();
        String newRecordNumber = "CR-002";
        Instant openedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Instant closedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        ClinicalRecordStatusId newStatusId = ClinicalRecordStatusId.generate();

        record.update(newPatientId, newRecordNumber, openedAt, closedAt, newStatusId);

        assertEquals(newPatientId, record.patientId());
        assertEquals(newRecordNumber, record.recordNumber());
        assertEquals(openedAt, record.openedAt());
        assertEquals(closedAt, record.closedAt());
        assertEquals(newStatusId, record.statusId());
        assertNotNull(record.updatedAt());

        var events = record.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ClinicalRecordUpdatedEvent);
    }

    @Test
    void update_NullPatientId_ThrowsException() {
        ClinicalRecord record = ClinicalRecord.register(PatientId.generate(), "CR-001", ClinicalRecordStatusId.generate(), ProfessionalId.generate());
        assertThrows(NullPointerException.class,
                () -> record.update(null, "CR-002", null, null, ClinicalRecordStatusId.generate()));
    }

    @Test
    void update_RecordNumberBlank_ThrowsException() {
        ClinicalRecord record = ClinicalRecord.register(PatientId.generate(), "CR-001", ClinicalRecordStatusId.generate(), ProfessionalId.generate());
        assertThrows(InvalidClinicalRecordException.class,
                () -> record.update(PatientId.generate(), " ", null, null, ClinicalRecordStatusId.generate()));
    }

    @Test
    void update_RecordNumberTooLong_ThrowsException() {
        ClinicalRecord record = ClinicalRecord.register(PatientId.generate(), "CR-001", ClinicalRecordStatusId.generate(), ProfessionalId.generate());
        String longRecordNumber = "a".repeat(51);
        assertThrows(InvalidClinicalRecordException.class,
                () -> record.update(PatientId.generate(), longRecordNumber, null, null, ClinicalRecordStatusId.generate()));
    }

    @Test
    void update_NullStatusId_ThrowsException() {
        ClinicalRecord record = ClinicalRecord.register(PatientId.generate(), "CR-001", ClinicalRecordStatusId.generate(), ProfessionalId.generate());
        assertThrows(NullPointerException.class,
                () -> record.update(PatientId.generate(), "CR-002", null, null, null));
    }

    @Test
    void delete_RegistersDeletedEvent() {
        ClinicalRecord record = ClinicalRecord.register(PatientId.generate(), "CR-001", ClinicalRecordStatusId.generate(), ProfessionalId.generate());
        int initialEventCount = record.domainEvents().size();

        record.delete();

        var events = record.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ClinicalRecordDeletedEvent);
    }
}