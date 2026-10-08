package com.mindconnect.domain.clinicalrecord.risklevel;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.risklevel.event.RiskLevelDeletedEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.event.RiskLevelRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.event.RiskLevelUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.risklevel.exception.InvalidRiskLevelException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RiskLevelTest {

    @Test
    void register_ValidCodeNameAndSeverity_CreatesLevelWithEvents() {
        String code = "LOW";
        String name = "Low Risk";
        int severity = 1;

        RiskLevel level = RiskLevel.register(code, name, severity);

        assertNotNull(level.id());
        assertEquals(code, level.code());
        assertEquals(name, level.name());
        assertEquals(severity, level.severity());
        assertTrue(level.active());
        assertNotNull(level.createdAt());
        assertNotNull(level.updatedAt());

        var events = level.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof RiskLevelRegisteredEvent);
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register(" ", "name", 1));
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register("", "name", 1));
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register(null, "name", 1));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register("code", " ", 1));
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register("code", "", 1));
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register("code", null, 1));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register(longCode, "name", 1));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidRiskLevelException.class, () -> RiskLevel.register("code", longName, 1));
    }

    @Test
    void restore_CreatesLevelWithoutEvents() {
        RiskLevelId id = RiskLevelId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        RiskLevel level = RiskLevel.restore(id, "code", "name", true, 1, now, now);

        assertEquals(id, level.id());
        assertEquals("code", level.code());
        assertEquals("name", level.name());
        assertEquals(1, level.severity());
        assertTrue(level.active());
        assertEquals(now, level.createdAt());
        assertEquals(now, level.updatedAt());
        assertTrue(level.domainEvents().isEmpty());
    }

    @Test
    void restore_WithInactive_CreatesInactiveLevel() {
        RiskLevelId id = RiskLevelId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        RiskLevel level = RiskLevel.restore(id, "code", "name", false, 1, now, now);

        assertFalse(level.active());
    }

    @Test
    void update_ValidCodeNameAndSeverity_UpdatesFieldsAndRecordsEvent() {
        RiskLevel level = RiskLevel.register("LOW", "Low Risk", 1);
        int initialEventCount = level.domainEvents().size();

        level.update("MODERATE", "Moderate Risk", false, 2);

        assertEquals("MODERATE", level.code());
        assertEquals("Moderate Risk", level.name());
        assertEquals(2, level.severity());
        assertFalse(level.active());
        assertNotNull(level.updatedAt());

        var events = level.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof RiskLevelUpdatedEvent);
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        RiskLevel level = RiskLevel.register("LOW", "Low Risk", 1);
        assertThrows(InvalidRiskLevelException.class, () -> level.update(" ", "name", true, 1));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        RiskLevel level = RiskLevel.register("LOW", "Low Risk", 1);
        assertThrows(InvalidRiskLevelException.class, () -> level.update("code", " ", true, 1));
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        RiskLevel level = RiskLevel.register("LOW", "Low Risk", 1);
        assertTrue(level.active());

        level.update("LOW", "Low Risk", false, 1);

        assertFalse(level.active());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        RiskLevel level = RiskLevel.register("LOW", "Low Risk", 1);
        int initialEventCount = level.domainEvents().size();

        level.delete();

        var events = level.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof RiskLevelDeletedEvent);
    }
}