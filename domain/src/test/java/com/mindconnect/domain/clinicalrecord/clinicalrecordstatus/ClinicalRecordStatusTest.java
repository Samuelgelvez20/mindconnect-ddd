package com.mindconnect.domain.clinicalrecord.clinicalrecordstatus;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event.ClinicalRecordStatusDeletedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event.ClinicalRecordStatusRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.event.ClinicalRecordStatusUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.exception.InvalidClinicalRecordStatusException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClinicalRecordStatusTest {

    @Test
    void register_ValidCodeAndName_CreatesStatusWithEvents() {
        String code = "OPEN";
        String name = "Open";

        ClinicalRecordStatus status = ClinicalRecordStatus.register(code, name);

        assertNotNull(status.id());
        assertEquals(code, status.code());
        assertEquals(name, status.name());
        assertNotNull(status.createdAt());
        assertNotNull(status.updatedAt());

        var events = status.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ClinicalRecordStatusRegisteredEvent);
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register(" ", "name"));
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register("", "name"));
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register(null, "name"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register("code", " "));
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register("code", ""));
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register("code", null));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register(longCode, "name"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidClinicalRecordStatusException.class, () -> ClinicalRecordStatus.register("code", longName));
    }

    @Test
    void restore_CreatesStatusWithoutEvents() {
        ClinicalRecordStatusId id = ClinicalRecordStatusId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ClinicalRecordStatus status = ClinicalRecordStatus.restore(id, "code", "name", now, now);

        assertEquals(id, status.id());
        assertEquals("code", status.code());
        assertEquals("name", status.name());
        assertEquals(now, status.createdAt());
        assertEquals(now, status.updatedAt());
        assertTrue(status.domainEvents().isEmpty());
    }

    @Test
    void update_ValidCodeAndName_UpdatesFieldsAndRecordsEvent() {
        ClinicalRecordStatus status = ClinicalRecordStatus.register("OPEN", "Open");
        int initialEventCount = status.domainEvents().size();

        status.update("CLOSED", "Closed");

        assertEquals("CLOSED", status.code());
        assertEquals("Closed", status.name());
        assertNotNull(status.updatedAt());

        var events = status.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ClinicalRecordStatusUpdatedEvent);
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        ClinicalRecordStatus status = ClinicalRecordStatus.register("OPEN", "Open");
        assertThrows(InvalidClinicalRecordStatusException.class, () -> status.update(" ", "name"));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        ClinicalRecordStatus status = ClinicalRecordStatus.register("OPEN", "Open");
        assertThrows(InvalidClinicalRecordStatusException.class, () -> status.update("code", " "));
    }

    @Test
    void delete_RegistersDeletedEvent() {
        ClinicalRecordStatus status = ClinicalRecordStatus.register("OPEN", "Open");
        int initialEventCount = status.domainEvents().size();

        status.delete();

        var events = status.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ClinicalRecordStatusDeletedEvent);
    }
}