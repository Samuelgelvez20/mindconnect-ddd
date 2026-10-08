package com.mindconnect.domain.patient.patient.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.patient.patient.event.PatientDeletedEvent;
import com.mindconnect.domain.patient.patient.event.PatientRegisteredEvent;
import com.mindconnect.domain.patient.patient.event.PatientUpdatedEvent;
import com.mindconnect.domain.patient.patient.exception.InvalidPatientException;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

class PatientTest {

    @Test
    void shouldRegisterActivePatientAndRecordEvent() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient = Patient.register(
                documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                java.time.LocalDate.of(1990, 5, 15),
                biologicalSexId, genderIdentityId,
                "juan.perez@example.com",
                "3001234567", "Calle 123 #45-67",
                ProfessionalId.generate(), cityId);

        assertNotNull(patient.id());
        assertEquals(documentTypeId, patient.documentTypeId());
        assertEquals("12345678", patient.documentNumber());
        assertEquals("Juan", patient.firstName());
        assertEquals("Carlos", patient.middleName());
        assertEquals("Perez", patient.lastName());
        assertEquals("Gomez", patient.secondLastName());
        assertEquals(java.time.LocalDate.of(1990, 5, 15), patient.birthDate());
        assertEquals(biologicalSexId, patient.biologicalSexId());
        assertEquals(genderIdentityId, patient.genderIdentityId());
        assertEquals("juan.perez@example.com", patient.email());
        assertEquals("3001234567", patient.phone());
        assertEquals("Calle 123 #45-67", patient.address());
        assertTrue(patient.active());
        assertEquals(patient.createdAt(), patient.updatedAt());

        assertEquals(1, patient.domainEvents().size());
        PatientRegisteredEvent event = assertInstanceOf(
                PatientRegisteredEvent.class, patient.domainEvents().getFirst());
        assertEquals(patient.id(), event.id());
    }

    @Test
    void shouldTrimRequiredTextAndNullifyOptionalBlank() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient = Patient.register(
                documentTypeId, "  12345678  ", "  Juan  ", "  ", "  Perez  ", "  ",
                java.time.LocalDate.of(1990, 5, 15),
                biologicalSexId, genderIdentityId,
                "  juan.perez@example.com  ",
                "  3001234567  ", "  ",
                ProfessionalId.generate(), cityId);

        assertEquals("12345678", patient.documentNumber());
        assertEquals("Juan", patient.firstName());
        assertNull(patient.middleName());
        assertEquals("Perez", patient.lastName());
        assertNull(patient.secondLastName());
        assertEquals("juan.perez@example.com", patient.email());
        assertEquals("3001234567", patient.phone());
        assertNull(patient.address());
    }

    @Test
    void shouldRejectBlankDocumentNumber() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "  ", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectNullDocumentNumber() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), null, "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectBlankFirstName() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "  ", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectBlankLastName() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "  ", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectBlankEmail() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "  ", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectNullBirthDate() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        null,
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectNullBiologicalSexId() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        null, GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectNullGenderIdentityId() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), null,
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectNullCityId() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), null));
    }

    @Test
    void shouldRejectNullCreatedBy() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        null, CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongDocumentNumber() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "x".repeat(Patient.DOCUMENT_NUMBER_MAX_LENGTH + 1), "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongFirstName() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "x".repeat(Patient.FIRST_NAME_MAX_LENGTH + 1), null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongMiddleName() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", "x".repeat(Patient.MIDDLE_NAME_MAX_LENGTH + 1), "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongLastName() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "x".repeat(Patient.LAST_NAME_MAX_LENGTH + 1), null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongSecondLastName() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", "x".repeat(Patient.SECOND_LAST_NAME_MAX_LENGTH + 1),
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongEmail() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "x".repeat(Patient.EMAIL_MAX_LENGTH + 1) + "@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongPhone() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", "x".repeat(Patient.PHONE_MAX_LENGTH + 1), null,
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldRejectTooLongAddress() {
        assertThrows(InvalidPatientException.class,
                () -> Patient.register(
                        DocumentTypeId.generate(), "12345678", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, "x".repeat(Patient.ADDRESS_MAX_LENGTH + 1),
                        ProfessionalId.generate(), CityMunicipalityId.generate()));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient = Patient.register(
                documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                java.time.LocalDate.of(1990, 5, 15),
                biologicalSexId, genderIdentityId,
                "juan.perez@example.com", "3001234567", "Calle 123",
                ProfessionalId.generate(), cityId);
        patient.clearDomainEvents();

        DocumentTypeId newDocumentTypeId = DocumentTypeId.generate();
        GenderId newBiologicalSexId = GenderId.generate();
        GenderId newGenderIdentityId = GenderId.generate();
        ProfessionalId updatedBy = ProfessionalId.generate();
        CityMunicipalityId newCityId = CityMunicipalityId.generate();

        patient.update(
                newDocumentTypeId, "87654321", "Maria", "Luisa", "Gomez", "Lopez",
                java.time.LocalDate.of(1985, 8, 20),
                newBiologicalSexId, newGenderIdentityId,
                "maria.lopez@example.com", "3109876543", "Carrera 45 #67-89",
                false, ProfessionalId.generate(), CityMunicipalityId.generate());

        assertEquals(newDocumentTypeId, patient.documentTypeId());
        assertEquals("87654321", patient.documentNumber());
        assertEquals("Maria", patient.firstName());
        assertEquals("Luisa", patient.middleName());
        assertEquals("Gomez", patient.lastName());
        assertEquals("Lopez", patient.secondLastName());
        assertEquals(java.time.LocalDate.of(1985, 8, 20), patient.birthDate());
        assertEquals(newBiologicalSexId, patient.biologicalSexId());
        assertEquals(newGenderIdentityId, patient.genderIdentityId());
        assertEquals("maria.lopez@example.com", patient.email());
        assertFalse(patient.active());
        assertFalse(patient.updatedAt().isBefore(patient.createdAt()));

        assertEquals(1, patient.domainEvents().size());
        PatientUpdatedEvent event = assertInstanceOf(
                PatientUpdatedEvent.class, patient.domainEvents().getFirst());
        assertEquals(patient.id(), event.id());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient = Patient.register(
                documentTypeId, "12345678", "Juan", null, "Perez", null,
                java.time.LocalDate.of(1990, 5, 15),
                GenderId.generate(), GenderId.generate(),
                "juan@example.com", null, null,
                ProfessionalId.generate(), cityId);
        patient.clearDomainEvents();

        assertThrows(InvalidPatientException.class,
                () -> patient.update(
                        documentTypeId, "  ", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        true, ProfessionalId.generate(), cityId));
        assertTrue(patient.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient = Patient.register(
                documentTypeId, "12345678", "Juan", null, "Perez", null,
                java.time.LocalDate.of(1990, 5, 15),
                biologicalSexId, genderIdentityId,
                "juan@example.com", null, null,
                ProfessionalId.generate(), cityId);
        patient.clearDomainEvents();

        patient.delete();

        assertEquals(1, patient.domainEvents().size());
        PatientDeletedEvent event = assertInstanceOf(
                PatientDeletedEvent.class, patient.domainEvents().getLast());
        assertEquals(patient.id(), event.id());
    }
}