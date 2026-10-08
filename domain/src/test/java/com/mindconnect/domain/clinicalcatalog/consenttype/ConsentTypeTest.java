package com.mindconnect.domain.clinicalcatalog.consenttype;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalcatalog.consenttype.event.ConsentTypeDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.event.ConsentTypeRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.event.ConsentTypeUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.consenttype.exception.InvalidConsentTypeException;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.aggregate.ConsentType;
import com.mindconnect.domain.clinicalcatalog.consenttype.model.valueobject.ConsentTypeId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsentTypeTest {

    @Test
    void register_ValidCodeNameAndDescription_CreatesTypeWithEvents() {
        String code = "INFORMED";
        String name = "Informed Consent";
        String description = "Standard informed consent";

        ConsentType type = ConsentType.register(code, name, description);

        assertNotNull(type.id());
        assertEquals(code, type.code());
        assertEquals(name, type.name());
        assertEquals(description, type.description());
        assertTrue(type.isActive());
        assertNotNull(type.createdAt());
        assertNotNull(type.updatedAt());

        var events = type.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ConsentTypeRegisteredEvent);
    }

    @Test
    void register_ValidCodeAndNameWithNullDescription_CreatesTypeWithNullDescription() {
        String code = "INFORMED";
        String name = "Informed Consent";

        ConsentType type = ConsentType.register(code, name, null);

        assertEquals(code, type.code());
        assertEquals(name, type.name());
        assertNull(type.description());
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register(" ", "name", "desc"));
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register("", "name", "desc"));
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register(null, "name", "desc"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register("code", " ", "desc"));
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register("code", "", "desc"));
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register("code", null, "desc"));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register(longCode, "name", "desc"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidConsentTypeException.class, () -> ConsentType.register("code", longName, "desc"));
    }

    @Test
    void register_DescriptionWithWhitespace_TrimsWhitespace() {
        String description = "  Standard informed consent  ";
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", description);
        assertEquals("Standard informed consent", type.description());
    }

    @Test
    void register_DescriptionBlankString_BecomesNull() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "   ");
        assertNull(type.description());
    }

    @Test
    void restore_CreatesTypeWithoutEvents() {
        ConsentTypeId id = ConsentTypeId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ConsentType type = ConsentType.restore(id, "code", "name", "desc", true, now, now);

        assertEquals(id, type.id());
        assertEquals("code", type.code());
        assertEquals("name", type.name());
        assertEquals("desc", type.description());
        assertTrue(type.isActive());
        assertEquals(now, type.createdAt());
        assertEquals(now, type.updatedAt());
        assertTrue(type.domainEvents().isEmpty());
    }

    @Test
    void restore_WithNullDescription_CreatesTypeWithNullDescription() {
        ConsentTypeId id = ConsentTypeId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ConsentType type = ConsentType.restore(id, "code", "name", null, true, now, now);

        assertNull(type.description());
    }

    @Test
    void update_ValidCodeNameAndDescription_UpdatesFieldsAndRecordsEvent() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "Old description");
        int initialEventCount = type.domainEvents().size();

        type.update("WITHDRAWAL", "Withdrawal Consent", "New description", false);

        assertEquals("WITHDRAWAL", type.code());
        assertEquals("Withdrawal Consent", type.name());
        assertEquals("New description", type.description());
        assertNotNull(type.updatedAt());
        assertFalse(type.isActive());

        var events = type.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ConsentTypeUpdatedEvent);
    }

    @Test
    void update_WithNullDescription_UpdatesDescriptionToNull() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "Old description");

        type.update("WITHDRAWAL", "Withdrawal Consent", null, true);

        assertNull(type.description());
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        assertThrows(InvalidConsentTypeException.class, () -> type.update(" ", "name", "desc", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        assertThrows(InvalidConsentTypeException.class, () -> type.update("code", " ", "desc", true));
    }

    @Test
    void update_CodeTooLong_ThrowsException() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        String longCode = "a".repeat(21);
        assertThrows(InvalidConsentTypeException.class, () -> type.update(longCode, "name", "desc", true));
    }

    @Test
    void update_NameTooLong_ThrowsException() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        String longName = "a".repeat(51);
        assertThrows(InvalidConsentTypeException.class, () -> type.update("code", longName, "desc", true));
    }

    @Test
    void update_DescriptionWithWhitespace_TrimsWhitespace() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        type.update("WITHDRAWAL", "Withdrawal Consent", "  New Description  ", true);
        assertEquals("New Description", type.description());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        assertTrue(type.isActive());

        type.update("INFORMED", "Informed Consent", "desc", false);

        assertFalse(type.isActive());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        ConsentType type = ConsentType.register("INFORMED", "Informed Consent", "desc");
        int initialEventCount = type.domainEvents().size();

        type.delete();

        var events = type.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ConsentTypeDeletedEvent);
    }
}