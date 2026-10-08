package com.mindconnect.domain.clinicalcatalog.assessmenttype;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalcatalog.assessmenttype.event.AssessmentTypeDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.event.AssessmentTypeRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.event.AssessmentTypeUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.exception.InvalidAssessmentTypeException;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssessmentTypeTest {

    @Test
    void register_ValidCodeNameAndDescription_CreatesTypeWithEvents() {
        String code = "PHQ9";
        String name = "PHQ-9";
        String description = "Patient Health Questionnaire";

        AssessmentType type = AssessmentType.register(code, name, description);

        assertNotNull(type.id());
        assertEquals(code, type.code());
        assertEquals(name, type.name());
        assertEquals(description, type.description());
        assertTrue(type.isActive());
        assertNotNull(type.createdAt());
        assertNotNull(type.updatedAt());

        var events = type.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AssessmentTypeRegisteredEvent);
    }

    @Test
    void register_ValidCodeAndNameWithNullDescription_CreatesTypeWithNullDescription() {
        String code = "PHQ9";
        String name = "PHQ-9";

        AssessmentType type = AssessmentType.register(code, name, null);

        assertEquals(code, type.code());
        assertEquals(name, type.name());
        assertNull(type.description());
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register(" ", "name", "desc"));
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register("", "name", "desc"));
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register(null, "name", "desc"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register("code", " ", "desc"));
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register("code", "", "desc"));
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register("code", null, "desc"));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register(longCode, "name", "desc"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidAssessmentTypeException.class, () -> AssessmentType.register("code", longName, "desc"));
    }

    @Test
    void register_DescriptionWithWhitespace_TrimsWhitespace() {
        String description = "  Patient Health Questionnaire  ";
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", description);
        assertEquals("Patient Health Questionnaire", type.description());
    }

    @Test
    void register_DescriptionBlankString_BecomesNull() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "   ");
        assertNull(type.description());
    }

    @Test
    void restore_CreatesTypeWithoutEvents() {
        AssessmentTypeId id = AssessmentTypeId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        AssessmentType type = AssessmentType.restore(id, "code", "name", "desc", true, now, now);

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
        AssessmentTypeId id = AssessmentTypeId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        AssessmentType type = AssessmentType.restore(id, "code", "name", null, true, now, now);

        assertNull(type.description());
    }

    @Test
    void update_ValidCodeNameAndDescription_UpdatesFieldsAndRecordsEvent() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "Old description");
        int initialEventCount = type.domainEvents().size();

        type.update("GAD7", "GAD-7", "New description", false);

        assertEquals("GAD7", type.code());
        assertEquals("GAD-7", type.name());
        assertEquals("New description", type.description());
        assertNotNull(type.updatedAt());
        assertFalse(type.isActive());

        var events = type.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof AssessmentTypeUpdatedEvent);
    }

    @Test
    void update_WithNullDescription_UpdatesDescriptionToNull() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "Old description");

        type.update("GAD7", "GAD-7", null, true);

        assertNull(type.description());
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        assertThrows(InvalidAssessmentTypeException.class, () -> type.update(" ", "name", "desc", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        assertThrows(InvalidAssessmentTypeException.class, () -> type.update("code", " ", "desc", true));
    }

    @Test
    void update_CodeTooLong_ThrowsException() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        String longCode = "a".repeat(21);
        assertThrows(InvalidAssessmentTypeException.class, () -> type.update(longCode, "name", "desc", true));
    }

    @Test
    void update_NameTooLong_ThrowsException() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        String longName = "a".repeat(51);
        assertThrows(InvalidAssessmentTypeException.class, () -> type.update("code", longName, "desc", true));
    }

    @Test
    void update_DescriptionWithWhitespace_TrimsWhitespace() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        type.update("GAD7", "GAD-7", "  New Description  ", true);
        assertEquals("New Description", type.description());
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        assertTrue(type.isActive());

        type.update("PHQ9", "PHQ-9", "desc", false);

        assertFalse(type.isActive());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        AssessmentType type = AssessmentType.register("PHQ9", "PHQ-9", "desc");
        int initialEventCount = type.domainEvents().size();

        type.delete();

        var events = type.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof AssessmentTypeDeletedEvent);
    }
}