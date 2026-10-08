package com.mindconnect.domain.clinicalcatalog.diagnosticsystem;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event.DiagnosticSystemDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event.DiagnosticSystemRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.event.DiagnosticSystemUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.exception.InvalidDiagnosticSystemException;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiagnosticSystemTest {

    @Test
    void register_ValidCodeNameAndVersion_CreatesSystemWithEvents() {
        String code = "ICD10";
        String name = "ICD-10";
        String version = "2019";

        DiagnosticSystem system = DiagnosticSystem.register(code, name, version);

        assertNotNull(system.id());
        assertEquals(code, system.code());
        assertEquals(name, system.name());
        assertEquals(version, system.version());
        assertTrue(system.isActive());
        assertNotNull(system.createdAt());
        assertNotNull(system.updatedAt());

        var events = system.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof DiagnosticSystemRegisteredEvent);
    }

    @Test
    void register_ValidCodeAndNameWithNullVersion_CreatesSystemWithNullVersion() {
        String code = "ICD10";
        String name = "ICD-10";

        DiagnosticSystem system = DiagnosticSystem.register(code, name, null);

        assertEquals(code, system.code());
        assertEquals(name, system.name());
        assertNull(system.version());
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register(" ", "name", "v1"));
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register("", "name", "v1"));
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register(null, "name", "v1"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register("code", " ", "v1"));
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register("code", "", "v1"));
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register("code", null, "v1"));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register(longCode, "name", "v1"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register("code", longName, "v1"));
    }

    @Test
    void register_VersionTooLong_ThrowsException() {
        String longVersion = "a".repeat(21);
        assertThrows(InvalidDiagnosticSystemException.class, () -> DiagnosticSystem.register("code", "name", longVersion));
    }

    @Test
    void register_VersionWithWhitespace_TrimsWhitespace() {
        String version = "  v1.0  ";
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", version);
        assertEquals("v1.0", system.version());
    }

    @Test
    void register_VersionBlankString_BecomesNull() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "   ");
        assertNull(system.version());
    }

    @Test
    void restore_CreatesSystemWithoutEvents() {
        DiagnosticSystemId id = DiagnosticSystemId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        DiagnosticSystem system = DiagnosticSystem.restore(id, "code", "name", "v1", true, now, now);

        assertEquals(id, system.id());
        assertEquals("code", system.code());
        assertEquals("name", system.name());
        assertEquals("v1", system.version());
        assertTrue(system.isActive());
        assertEquals(now, system.createdAt());
        assertEquals(now, system.updatedAt());
        assertTrue(system.domainEvents().isEmpty());
    }

    @Test
    void restore_WithNullVersion_CreatesSystemWithNullVersion() {
        DiagnosticSystemId id = DiagnosticSystemId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        DiagnosticSystem system = DiagnosticSystem.restore(id, "code", "name", null, true, now, now);

        assertNull(system.version());
    }

    @Test
    void update_ValidCodeNameAndVersion_UpdatesFieldsAndRecordsEvent() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "2019");
        int initialEventCount = system.domainEvents().size();

        system.update("ICD11", "ICD-11", "2022", false);

        assertEquals("ICD11", system.code());
        assertEquals("ICD-11", system.name());
        assertEquals("2022", system.version());
        assertNotNull(system.updatedAt());
        assertFalse(system.isActive());

        var events = system.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof DiagnosticSystemUpdatedEvent);
    }

    @Test
    void update_WithNullVersion_UpdatesVersionToNull() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "2019");

        system.update("ICD11", "ICD-11", null, true);

        assertNull(system.version());
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "v1");
        assertThrows(InvalidDiagnosticSystemException.class, () -> system.update(" ", "name", "v1", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "v1");
        assertThrows(InvalidDiagnosticSystemException.class, () -> system.update("code", " ", "v1", true));
    }

    @Test
    void update_VersionTooLong_ThrowsException() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "v1");
        String longVersion = "a".repeat(21);
        assertThrows(InvalidDiagnosticSystemException.class, () -> system.update("code", "name", longVersion, true));
    }

    @Test
    void update_VersionWithWhitespace_TrimsWhitespace() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "v1");
        system.update("ICD11", "ICD-11", "  v2.0  ", true);
        assertEquals("v2.0", system.version());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "v1");
        assertTrue(system.isActive());

        system.update("ICD10", "ICD-10", "v1", false);

        assertFalse(system.isActive());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        DiagnosticSystem system = DiagnosticSystem.register("ICD10", "ICD-10", "v1");
        int initialEventCount = system.domainEvents().size();

        system.delete();

        var events = system.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof DiagnosticSystemDeletedEvent);
    }
}