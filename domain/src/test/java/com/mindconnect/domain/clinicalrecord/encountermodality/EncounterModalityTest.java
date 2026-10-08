package com.mindconnect.domain.clinicalrecord.encountermodality;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.encountermodality.event.EncounterModalityDeletedEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.event.EncounterModalityRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.event.EncounterModalityUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.encountermodality.exception.InvalidEncounterModalityException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate.EncounterModality;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncounterModalityTest {

    @Test
    void register_ValidCodeAndName_CreatesModalityWithEvents() {
        String code = "IN_PERSON";
        String name = "In Person";

        EncounterModality modality = EncounterModality.register(code, name);

        assertNotNull(modality.id());
        assertEquals(code, modality.code());
        assertEquals(name, modality.name());
        assertTrue(modality.active());
        assertNotNull(modality.createdAt());
        assertNotNull(modality.updatedAt());

        var events = modality.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof EncounterModalityRegisteredEvent);
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register(" ", "name"));
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register("", "name"));
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register(null, "name"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register("code", " "));
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register("code", ""));
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register("code", null));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register(longCode, "name"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidEncounterModalityException.class, () -> EncounterModality.register("code", longName));
    }

    @Test
    void restore_CreatesModalityWithoutEvents() {
        EncounterModalityId id = EncounterModalityId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        EncounterModality modality = EncounterModality.restore(id, "code", "name", true, now, now);

        assertEquals(id, modality.id());
        assertEquals("code", modality.code());
        assertEquals("name", modality.name());
        assertTrue(modality.active());
        assertEquals(now, modality.createdAt());
        assertEquals(now, modality.updatedAt());
        assertTrue(modality.domainEvents().isEmpty());
    }

    @Test
    void restore_WithInactive_CreatesInactiveModality() {
        EncounterModalityId id = EncounterModalityId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        EncounterModality modality = EncounterModality.restore(id, "code", "name", false, now, now);

        assertFalse(modality.active());
    }

    @Test
    void update_ValidCodeNameAndActive_UpdatesFieldsAndRecordsEvent() {
        EncounterModality modality = EncounterModality.register("IN_PERSON", "In Person");
        int initialEventCount = modality.domainEvents().size();

        modality.update("VIDEO", "Video Call", false);

        assertEquals("VIDEO", modality.code());
        assertEquals("Video Call", modality.name());
        assertFalse(modality.active());
        assertNotNull(modality.updatedAt());

        var events = modality.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterModalityUpdatedEvent);
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        EncounterModality modality = EncounterModality.register("IN_PERSON", "In Person");
        assertThrows(InvalidEncounterModalityException.class, () -> modality.update(" ", "name", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        EncounterModality modality = EncounterModality.register("IN_PERSON", "In Person");
        assertThrows(InvalidEncounterModalityException.class, () -> modality.update("code", " ", true));
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        EncounterModality modality = EncounterModality.register("IN_PERSON", "In Person");
        assertTrue(modality.active());

        modality.update("IN_PERSON", "In Person", false);

        assertFalse(modality.active());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        EncounterModality modality = EncounterModality.register("IN_PERSON", "In Person");
        int initialEventCount = modality.domainEvents().size();

        modality.delete();

        var events = modality.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof EncounterModalityDeletedEvent);
    }
}