package com.mindconnect.domain.clinicalrecord.encountertype;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.encountertype.event.EncounterTypeDeletedEvent;
import com.mindconnect.domain.clinicalrecord.encountertype.event.EncounterTypeRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.encountertype.event.EncounterTypeUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.encountertype.exception.InvalidEncounterTypeException;
import com.mindconnect.domain.clinicalrecord.encountertype.model.aggregate.EncounterType;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncounterTypeTest {

    @Test
    void register_ValidCodeAndName_CreatesTypeWithEvents() {
        String code = "CONSULT";
        String name = "Consultation";

        EncounterType type = EncounterType.register(code, name);

        assertNotNull(type.id());
        assertEquals(code, type.code());
        assertEquals(name, type.name());
        assertTrue(type.active());
        assertNotNull(type.createdAt());
        assertNotNull(type.updatedAt());

        var events = type.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof EncounterTypeRegisteredEvent);
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register(" ", "name"));
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register("", "name"));
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register(null, "name"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register("code", " "));
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register("code", ""));
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register("code", null));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register(longCode, "name"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidEncounterTypeException.class, () -> EncounterType.register("code", longName));
    }

    @Test
    void restore_CreatesTypeWithoutEvents() {
        EncounterTypeId id = EncounterTypeId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        EncounterType type = EncounterType.restore(id, "code", "name", true, now, now);

        assertEquals(id, type.id());
        assertEquals("code", type.code());
        assertEquals("name", type.name());
        assertTrue(type.active());
        assertEquals(now, type.createdAt());
        assertEquals(now, type.updatedAt());
        assertTrue(type.domainEvents().isEmpty());
    }

    @Test
    void restore_WithInactive_CreatesInactiveType() {
        EncounterTypeId id = EncounterTypeId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        EncounterType type = EncounterType.restore(id, "code", "name", false, now, now);

        assertFalse(type.active());
    }

    @Test
    void update_ValidCodeNameAndActive_UpdatesFieldsAndRecordsEvent() {
        EncounterType type = EncounterType.register("CONSULT", "Consultation");
        int initialEventCount = type.domainEvents().size();

        type.update("FOLLOWUP", "Follow-up", false);

        assertEquals("FOLLOWUP", type.code());
        assertEquals("Follow-up", type.name());
        assertFalse(type.active());
        assertNotNull(type.updatedAt());

        var events = type.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterTypeUpdatedEvent);
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        EncounterType type = EncounterType.register("CONSULT", "Consultation");
        assertThrows(InvalidEncounterTypeException.class, () -> type.update(" ", "name", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        EncounterType type = EncounterType.register("CONSULT", "Consultation");
        assertThrows(InvalidEncounterTypeException.class, () -> type.update("code", " ", true));
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        EncounterType type = EncounterType.register("CONSULT", "Consultation");
        assertTrue(type.active());

        type.update("CONSULT", "Consultation", false);

        assertFalse(type.active());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        EncounterType type = EncounterType.register("CONSULT", "Consultation");
        int initialEventCount = type.domainEvents().size();

        type.delete();

        var events = type.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterTypeDeletedEvent);
    }
}