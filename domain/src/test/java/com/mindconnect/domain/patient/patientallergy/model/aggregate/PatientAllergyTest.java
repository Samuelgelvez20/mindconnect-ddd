package com.mindconnect.domain.patient.patientallergy.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyDeletedEvent;
import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyRegisteredEvent;
import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyUpdatedEvent;
import com.mindconnect.domain.patient.patientallergy.exception.InvalidPatientAllergyException;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

class PatientAllergyTest {

    @Test
    void shouldRegisterActivePatientAllergyAndRecordEvent() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                patientId, "Penicillin", "Rash", "Mild", ProfessionalId.generate());

        assertNotNull(patientAllergy.id());
        assertEquals(patientId, patientAllergy.patientId());
        assertEquals("Penicillin", patientAllergy.substance());
        assertEquals("Rash", patientAllergy.reaction());
        assertEquals("Mild", patientAllergy.severity());
        assertTrue(patientAllergy.active());
        assertEquals(patientAllergy.recordedAt(), patientAllergy.createdAt());
        assertEquals(patientAllergy.createdAt(), patientAllergy.updatedAt());

        assertEquals(1, patientAllergy.domainEvents().size());
        PatientAllergyRegisteredEvent event = assertInstanceOf(
                PatientAllergyRegisteredEvent.class, patientAllergy.domainEvents().getFirst());
        assertEquals(patientAllergy.id(), event.id());
    }

    @Test
    void shouldTrimSubstanceAndNullifyOptionalBlank() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                patientId, "  Penicillin  ", "  ", "  ", ProfessionalId.generate());

        assertEquals("Penicillin", patientAllergy.substance());
        assertNull(patientAllergy.reaction());
        assertNull(patientAllergy.severity());
    }

    @Test
    void shouldRejectBlankSubstance() {
        assertThrows(InvalidPatientAllergyException.class,
                () -> PatientAllergy.register(
                        PatientId.generate(), "  ", null, null, ProfessionalId.generate()));
    }

    @Test
    void shouldRejectNullSubstance() {
        assertThrows(InvalidPatientAllergyException.class,
                () -> PatientAllergy.register(
                        PatientId.generate(), null, null, null, ProfessionalId.generate()));
    }

    @Test
    void shouldRejectTooLongSubstance() {
        assertThrows(InvalidPatientAllergyException.class,
                () -> PatientAllergy.register(
                        PatientId.generate(), "x".repeat(PatientAllergy.SUBSTANCE_MAX_LENGTH + 1), null, null,
                        ProfessionalId.generate()));
    }

    @Test
    void shouldRejectTooLongSeverity() {
        assertThrows(InvalidPatientAllergyException.class,
                () -> PatientAllergy.register(
                        PatientId.generate(), "Penicillin", null, "x".repeat(PatientAllergy.SEVERITY_MAX_LENGTH + 1),
                        ProfessionalId.generate()));
    }

    @Test
    void shouldRejectNullPatientId() {
        assertThrows(InvalidPatientAllergyException.class,
                () -> PatientAllergy.register(
                        null, "Penicillin", null, null, ProfessionalId.generate()));
    }

    @Test
    void shouldRejectNullRecordedBy() {
        assertThrows(InvalidPatientAllergyException.class,
                () -> PatientAllergy.register(
                        PatientId.generate(), "Penicillin", null, null, null));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                patientId, "Penicillin", "Rash", "Mild", ProfessionalId.generate());
        patientAllergy.clearDomainEvents();

        patientAllergy.update("Amoxicillin", "Hives", "Moderate", false);

        assertEquals("Amoxicillin", patientAllergy.substance());
        assertEquals("Hives", patientAllergy.reaction());
        assertEquals("Moderate", patientAllergy.severity());
        assertFalse(patientAllergy.active());
        assertFalse(patientAllergy.updatedAt().isBefore(patientAllergy.createdAt()));

        assertEquals(1, patientAllergy.domainEvents().size());
        PatientAllergyUpdatedEvent event = assertInstanceOf(
                PatientAllergyUpdatedEvent.class, patientAllergy.domainEvents().getFirst());
        assertEquals(patientAllergy.id(), event.id());
    }

    @Test
    void shouldNullifyBlankOptionalFieldsOnUpdate() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                patientId, "Penicillin", "Rash", "Mild", ProfessionalId.generate());
        patientAllergy.clearDomainEvents();

        patientAllergy.update("Amoxicillin", "  ", "  ", true);

        assertEquals("Amoxicillin", patientAllergy.substance());
        assertNull(patientAllergy.reaction());
        assertNull(patientAllergy.severity());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                patientId, "Penicillin", "Rash", "Mild", ProfessionalId.generate());
        patientAllergy.clearDomainEvents();

        assertThrows(InvalidPatientAllergyException.class,
                () -> patientAllergy.update("  ", "Rash", "Mild", true));
        assertTrue(patientAllergy.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                patientId, "Penicillin", "Rash", "Mild", ProfessionalId.generate());
        patientAllergy.clearDomainEvents();

        patientAllergy.delete();

        assertEquals(1, patientAllergy.domainEvents().size());
        PatientAllergyDeletedEvent event = assertInstanceOf(
                PatientAllergyDeletedEvent.class, patientAllergy.domainEvents().getLast());
        assertEquals(patientAllergy.id(), event.id());
    }
}