package com.mindconnect.domain.clinicalrecord.clinicalnote;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalrecord.clinicalnote.event.ClinicalNoteDeletedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.event.ClinicalNoteRegisteredEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.event.ClinicalNoteUpdatedEvent;
import com.mindconnect.domain.clinicalrecord.clinicalnote.exception.InvalidClinicalNoteException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClinicalNoteTest {

    @Test
    void register_ValidData_CreatesNoteWithEvents() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();

        ClinicalNote note = ClinicalNote.register(encounterId, professionalId, "sub", "obj", "ass", "pln", "notes");

        assertNotNull(note.id());
        assertEquals(encounterId, note.encounterId());
        assertEquals(professionalId, note.professionalId());
        assertEquals("sub", note.subjective());
        assertEquals("obj", note.objective());
        assertEquals("ass", note.assessment());
        assertEquals("pln", note.plan());
        assertEquals("notes", note.additionalNotes());
        assertNull(note.signedAt());
        assertNotNull(note.createdAt());
        assertNotNull(note.updatedAt());

        var events = note.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ClinicalNoteRegisteredEvent);
    }

    @Test
    void register_WithNullOptionalFields_CreatesNoteWithNulls() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();

        ClinicalNote note = ClinicalNote.register(encounterId, professionalId, null, null, null, null, null);

        assertNull(note.subjective());
        assertNull(note.objective());
        assertNull(note.assessment());
        assertNull(note.plan());
        assertNull(note.additionalNotes());
        assertNull(note.signedAt());
    }

    @Test
    void register_NullEncounterId_ThrowsException() {
        assertThrows(InvalidClinicalNoteException.class,
                () -> ClinicalNote.register(null, ProfessionalId.generate(), "sub", "obj", "ass", "pln", "notes"));
    }

    @Test
    void register_NullProfessionalId_ThrowsException() {
        assertThrows(InvalidClinicalNoteException.class,
                () -> ClinicalNote.register(EncounterId.generate(), null, "sub", "obj", "ass", "pln", "notes"));
    }

    @Test
    void restore_CreatesNoteWithoutEvents() {
        ClinicalNoteId id = ClinicalNoteId.generate();
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        Instant signedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ClinicalNote note = ClinicalNote.restore(
                id, encounterId, professionalId, "sub", "obj", "ass", "pln", "notes", signedAt, now, now);

        assertEquals(id, note.id());
        assertEquals(encounterId, note.encounterId());
        assertEquals(professionalId, note.professionalId());
        assertEquals("sub", note.subjective());
        assertEquals("obj", note.objective());
        assertEquals("ass", note.assessment());
        assertEquals("pln", note.plan());
        assertEquals("notes", note.additionalNotes());
        assertEquals(signedAt, note.signedAt());
        assertEquals(now, note.createdAt());
        assertEquals(now, note.updatedAt());
        assertTrue(note.domainEvents().isEmpty());
    }

    @Test
    void restore_WithNullOptionalFields_CreatesNoteWithNulls() {
        ClinicalNoteId id = ClinicalNoteId.generate();
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        ClinicalNote note = ClinicalNote.restore(
                id, encounterId, professionalId, null, null, null, null, null, null, now, now);

        assertNull(note.subjective());
        assertNull(note.objective());
        assertNull(note.assessment());
        assertNull(note.plan());
        assertNull(note.additionalNotes());
        assertNull(note.signedAt());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();

        ClinicalNote note = ClinicalNote.register(encounterId, professionalId, "sub", "obj", "ass", "pln", "notes");
        int initialEventCount = note.domainEvents().size();

        EncounterId newEncounterId = EncounterId.generate();
        ProfessionalId newProfessionalId = ProfessionalId.generate();
        Instant signedAt = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        note.update(newEncounterId, newProfessionalId, "new sub", "new obj", "new ass", "new pln", "new notes", signedAt);

        assertEquals(newEncounterId, note.encounterId());
        assertEquals(newProfessionalId, note.professionalId());
        assertEquals("new sub", note.subjective());
        assertEquals("new obj", note.objective());
        assertEquals("new ass", note.assessment());
        assertEquals("new pln", note.plan());
        assertEquals("new notes", note.additionalNotes());
        assertEquals(signedAt, note.signedAt());
        assertNotNull(note.updatedAt());

        var events = note.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ClinicalNoteUpdatedEvent);
    }

    @Test
    void update_WithNullOptionalFields_UpdatesToNull() {
        EncounterId encounterId = EncounterId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();

        ClinicalNote note = ClinicalNote.register(encounterId, professionalId, "sub", "obj", "ass", "pln", "notes");
        EncounterId newEncounterId = EncounterId.generate();
        ProfessionalId newProfessionalId = ProfessionalId.generate();

        note.update(newEncounterId, newProfessionalId, null, null, null, null, null, null);

        assertNull(note.subjective());
        assertNull(note.objective());
        assertNull(note.assessment());
        assertNull(note.plan());
        assertNull(note.additionalNotes());
        assertNull(note.signedAt());
    }

    @Test
    void delete_RegistersDeletedEvent() {
        ClinicalNote note = ClinicalNote.register(EncounterId.generate(), ProfessionalId.generate(), "sub", "obj", "ass", "pln", "notes");
        int initialEventCount = note.domainEvents().size();

        note.delete();

        var events = note.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof ClinicalNoteDeletedEvent);
    }
}