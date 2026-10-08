package com.mindconnect.domain.clinicalrecord.encounterstatus;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.encounterstatus.event.EncounterStatusDeletedEvent;
import com.mindconnect.domain.clinicalrecord.encounterstatus.event.EncounterStatusRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.encounterstatus.event.EncounterStatusUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.encounterstatus.exception.InvalidEncounterStatusException;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.aggregate.EncounterStatus;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncounterStatusTest {

    @Test
    void register_ValidCodeAndName_CreatesStatusWithEvents() {
        String code = "SCHEDULED";
        String name = "Scheduled";

        EncounterStatus status = EncounterStatus.register(code, name);

        assertNotNull(status.id());
        assertEquals(code, status.code());
        assertEquals(name, status.name());
        assertTrue(status.active());
        assertNotNull(status.createdAt());
        assertNotNull(status.updatedAt());

        var events = status.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof EncounterStatusRegisteredEvent);
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register(" ", "name"));
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register("", "name"));
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register(null, "name"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register("code", " "));
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register("code", ""));
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register("code", null));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register(longCode, "name"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidEncounterStatusException.class, () -> EncounterStatus.register("code", longName));
    }

    @Test
    void restore_CreatesStatusWithoutEvents() {
        EncounterStatusId id = EncounterStatusId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        EncounterStatus status = EncounterStatus.restore(id, "code", "name", true, now, now);

        assertEquals(id, status.id());
        assertEquals("code", status.code());
        assertEquals("name", status.name());
        assertTrue(status.active());
        assertEquals(now, status.createdAt());
        assertEquals(now, status.updatedAt());
        assertTrue(status.domainEvents().isEmpty());
    }

    @Test
    void restore_WithInactive_CreatesInactiveStatus() {
        EncounterStatusId id = EncounterStatusId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        EncounterStatus status = EncounterStatus.restore(id, "code", "name", false, now, now);

        assertFalse(status.active());
    }

    @Test
    void update_ValidCodeNameAndActive_UpdatesFieldsAndRecordsEvent() {
        EncounterStatus status = EncounterStatus.register("SCHEDULED", "Scheduled");
        int initialEventCount = status.domainEvents().size();

        status.update("COMPLETED", "Completed", false);

        assertEquals("COMPLETED", status.code());
        assertEquals("Completed", status.name());
        assertFalse(status.active());
        assertNotNull(status.updatedAt());

        var events = status.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterStatusUpdatedEvent);
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        EncounterStatus status = EncounterStatus.register("SCHEDULED", "Scheduled");
        assertThrows(InvalidEncounterStatusException.class, () -> status.update(" ", "name", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        EncounterStatus status = EncounterStatus.register("SCHEDULED", "Scheduled");
        assertThrows(InvalidEncounterStatusException.class, () -> status.update("code", " ", true));
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        EncounterStatus status = EncounterStatus.register("SCHEDULED", "Scheduled");
        assertTrue(status.active());

        status.update("SCHEDULED", "Scheduled", false);

        assertFalse(status.active());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        EncounterStatus status = EncounterStatus.register("SCHEDULED", "Scheduled");
        int initialEventCount = status.domainEvents().size();

        status.delete();

        var events = status.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterStatusDeletedEvent);
    }
}