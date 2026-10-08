package com.mindconnect.domain.clinicalrecord.mentalstatusexam;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.mentalstatusexam.event.MentalStatusExamDeletedEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.event.MentalStatusExamRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.event.MentalStatusExamUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.exception.InvalidMentalStatusExamException;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MentalStatusExamTest {

    @Test
    void register_ValidData_CreatesExamWithEvents() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId,
                "Well-groomed",
                "Cooperative",
                "Calm",
                "Alert",
                "Oriented x3",
                "Intact",
                "Intact",
                "Normal rate and tone",
                "Euthymic",
                "Appropriate",
                "Linear and logical",
                "No delusions",
                "No hallucinations",
                "Good",
                "Good",
                "Normal",
                "None",
                createdBy);

        assertNotNull(exam.id());
        assertEquals(encounterId, exam.encounterId());
        assertEquals(createdBy, exam.createdBy());
        assertEquals("Well-groomed", exam.appearance());
        assertEquals("Cooperative", exam.behavior());
        assertEquals("Calm", exam.attitude());
        assertEquals("Alert", exam.consciousness());
        assertEquals("Oriented x3", exam.orientation());
        assertEquals("Intact", exam.attention());
        assertEquals("Intact", exam.memory());
        assertEquals("Normal rate and tone", exam.speech());
        assertEquals("Euthymic", exam.mood());
        assertEquals("Appropriate", exam.affect());
        assertEquals("Linear and logical", exam.thoughtProcess());
        assertEquals("No delusions", exam.thoughtContent());
        assertEquals("No hallucinations", exam.perception());
        assertEquals("Good", exam.judgment());
        assertEquals("Good", exam.insight());
        assertEquals("Normal", exam.psychomotorActivity());
        assertEquals("None", exam.observations());
        assertNotNull(exam.createdAt());
        assertNotNull(exam.updatedAt());

        var events = exam.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof MentalStatusExamRegisteredEvent);
    }

    @Test
    void register_WithNullOptionalFields_CreatesExamWithNulls() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, createdBy);

        assertNull(exam.appearance());
        assertNull(exam.behavior());
        assertNull(exam.attitude());
        assertNull(exam.consciousness());
        assertNull(exam.orientation());
        assertNull(exam.attention());
        assertNull(exam.memory());
        assertNull(exam.speech());
        assertNull(exam.mood());
        assertNull(exam.affect());
        assertNull(exam.thoughtProcess());
        assertNull(exam.thoughtContent());
        assertNull(exam.perception());
        assertNull(exam.judgment());
        assertNull(exam.insight());
        assertNull(exam.psychomotorActivity());
        assertNull(exam.observations());
    }

    @Test
    void register_NullEncounterId_ThrowsException() {
        ProfessionalId createdBy = ProfessionalId.generate();

        assertThrows(InvalidMentalStatusExamException.class,
                () -> MentalStatusExam.register(null, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy));
    }

    @Test
    void register_NullCreatedBy_ThrowsException() {
        EncounterId encounterId = EncounterId.generate();

        assertThrows(InvalidMentalStatusExamException.class,
                () -> MentalStatusExam.register(encounterId, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, null));
    }

    @Test
    void restore_CreatesExamWithoutEvents() {
        MentalStatusExamId id = MentalStatusExamId.generate();
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        MentalStatusExam exam = MentalStatusExam.restore(
                id, encounterId,
                "appearance", "behavior", "attitude", "consciousness", "orientation",
                "attention", "memory", "speech", "mood", "affect", "thoughtProcess",
                "thoughtContent", "perception", "judgment", "insight",
                "psychomotorActivity", "observations",
                createdBy, now, now);

        assertEquals(id, exam.id());
        assertEquals(encounterId, exam.encounterId());
        assertEquals(createdBy, exam.createdBy());
        assertEquals("appearance", exam.appearance());
        assertEquals("behavior", exam.behavior());
        assertEquals("attitude", exam.attitude());
        assertEquals("consciousness", exam.consciousness());
        assertEquals("orientation", exam.orientation());
        assertEquals("attention", exam.attention());
        assertEquals("memory", exam.memory());
        assertEquals("speech", exam.speech());
        assertEquals("mood", exam.mood());
        assertEquals("affect", exam.affect());
        assertEquals("thoughtProcess", exam.thoughtProcess());
        assertEquals("thoughtContent", exam.thoughtContent());
        assertEquals("perception", exam.perception());
        assertEquals("judgment", exam.judgment());
        assertEquals("insight", exam.insight());
        assertEquals("psychomotorActivity", exam.psychomotorActivity());
        assertEquals("observations", exam.observations());
        assertEquals(now, exam.createdAt());
        assertEquals(now, exam.updatedAt());
        assertTrue(exam.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy);
        int initialEventCount = exam.domainEvents().size();

        EncounterId newEncounterId = EncounterId.generate();
        ProfessionalId newCreatedBy = ProfessionalId.generate();

        exam.update(
                newEncounterId,
                "new a", "new b", "new c", "new d", "new e", "new f", "new g", "new h",
                "new i", "new j", "new k", "new l", "new m", "new n", "new o", "new p",
                null, newCreatedBy);

        assertEquals(newEncounterId, exam.encounterId());
        assertEquals(newCreatedBy, exam.createdBy());
        assertEquals("new a", exam.appearance());
        assertEquals("new b", exam.behavior());
        assertEquals("new c", exam.attitude());
        assertEquals("new d", exam.consciousness());
        assertEquals("new e", exam.orientation());
        assertEquals("new f", exam.attention());
        assertEquals("new g", exam.memory());
        assertEquals("new h", exam.speech());
        assertEquals("new i", exam.mood());
        assertEquals("new j", exam.affect());
        assertEquals("new k", exam.thoughtProcess());
        assertEquals("new l", exam.thoughtContent());
        assertEquals("new m", exam.perception());
        assertEquals("new n", exam.judgment());
        assertEquals("new o", exam.insight());
        assertEquals("new p", exam.psychomotorActivity());

        var events = exam.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof MentalStatusExamUpdatedEvent);
    }

    @Test
    void update_WithNullFields_UpdatesToNull() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy);

        exam.update(
                EncounterId.generate(),
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, ProfessionalId.generate());

        assertNull(exam.appearance());
        assertNull(exam.behavior());
        assertNull(exam.attitude());
        assertNull(exam.consciousness());
        assertNull(exam.orientation());
        assertNull(exam.attention());
        assertNull(exam.memory());
        assertNull(exam.speech());
        assertNull(exam.mood());
        assertNull(exam.affect());
        assertNull(exam.thoughtProcess());
        assertNull(exam.thoughtContent());
        assertNull(exam.perception());
        assertNull(exam.judgment());
        assertNull(exam.insight());
        assertNull(exam.psychomotorActivity());
        assertNull(exam.observations());
    }

    @Test
    void update_NullEncounterId_ThrowsException() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy);

        assertThrows(InvalidMentalStatusExamException.class,
                () -> exam.update(null, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, ProfessionalId.generate()));
    }

    @Test
    void update_NullCreatedBy_ThrowsException() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy);

        assertThrows(InvalidMentalStatusExamException.class,
                () -> exam.update(EncounterId.generate(), "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy));
    }

    @Test
    void delete_RegistersDeletedEvent() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        MentalStatusExam exam = MentalStatusExam.register(
                encounterId, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", null, createdBy);
        int initialEventCount = exam.domainEvents().size();

        exam.delete();

        var events = exam.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof MentalStatusExamDeletedEvent);
    }
}