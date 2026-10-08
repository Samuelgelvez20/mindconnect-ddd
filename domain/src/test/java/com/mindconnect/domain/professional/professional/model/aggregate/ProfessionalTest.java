package com.mindconnect.domain.professional.professional.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.professional.professional.event.ProfessionalDeletedEvent;
import com.mindconnect.domain.professional.professional.event.ProfessionalRegisteredEvent;
import com.mindconnect.domain.professional.professional.event.ProfessionalUpdatedEvent;
import com.mindconnect.domain.professional.professional.exception.InvalidProfessionalException;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

class ProfessionalTest {

    @Test
    void shouldRegisterActiveProfessionalAndRecordEvent() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId,
                "12345678",
                "Juan",
                "Perez",
                professionalTypeId,
                "LIC123456",
                cityId);

        assertNotNull(professional.id());
        assertEquals(documentTypeId, professional.documentTypeId());
        assertEquals("12345678", professional.documentNumber());
        assertEquals("Juan", professional.firstName());
        assertEquals("Perez", professional.lastName());
        assertEquals(professionalTypeId, professional.professionalTypeId());
        assertEquals("LIC123456", professional.licenseNumber());
        assertTrue(professional.active());
        assertEquals(cityId, professional.cityId());
        assertEquals(professional.createdAt(), professional.updatedAt());

        assertEquals(1, professional.domainEvents().size());
        ProfessionalRegisteredEvent event = assertInstanceOf(
                ProfessionalRegisteredEvent.class, professional.domainEvents().getFirst());
        assertEquals(professional.id(), event.id());
    }

    @Test
    void shouldTrimTextFields() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId,
                "  12345678  ",
                "  Juan  ",
                "  Perez  ",
                professionalTypeId,
                "  LIC123456  ",
                cityId);

        assertEquals("12345678", professional.documentNumber());
        assertEquals("Juan", professional.firstName());
        assertEquals("Perez", professional.lastName());
        assertEquals("LIC123456", professional.licenseNumber());
    }

    @Test
    void shouldRejectBlankDocumentNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "  ", "Juan", "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectNullDocumentNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, null, "Juan", "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectBlankFirstName() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "  ", "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectNullFirstName() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", null, "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectBlankLastName() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "Juan", "  ", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectBlankLicenseNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "Juan", "Perez", professionalTypeId, "  ", cityId));
    }

    @Test
    void shouldRejectTooLongDocumentNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "x".repeat(Professional.DOCUMENT_NUMBER_MAX_LENGTH + 1), "Juan", "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectTooLongFirstName() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "x".repeat(Professional.FIRST_NAME_MAX_LENGTH + 1), "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectTooLongLastName() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "Juan", "x".repeat(Professional.LAST_NAME_MAX_LENGTH + 1), professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectTooLongLicenseNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "Juan", "Perez", professionalTypeId, "x".repeat(Professional.LICENSE_NUMBER_MAX_LENGTH + 1), cityId));
    }

    @Test
    void shouldRejectNullDocumentTypeId() {
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(null, "12345", "Juan", "Perez", professionalTypeId, "LIC123", cityId));
    }

    @Test
    void shouldRejectNullProfessionalTypeId() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "Juan", "Perez", null, "LIC123", cityId));
    }

    @Test
    void shouldRejectNullCityId() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();

        assertThrows(InvalidProfessionalException.class,
                () -> Professional.register(documentTypeId, "12345", "Juan", "Perez", professionalTypeId, "LIC123", null));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        professional.clearDomainEvents();

        DocumentTypeId newDocumentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId newProfessionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId newCityId = CityMunicipalityId.generate();

        professional.update(
                newDocumentTypeId,
                "87654321",
                "Maria",
                "Gomez",
                newProfessionalTypeId,
                "LIC987654",
                false,
                newCityId);

        assertEquals(newDocumentTypeId, professional.documentTypeId());
        assertEquals("87654321", professional.documentNumber());
        assertEquals("Maria", professional.firstName());
        assertEquals("Gomez", professional.lastName());
        assertEquals(newProfessionalTypeId, professional.professionalTypeId());
        assertEquals("LIC987654", professional.licenseNumber());
        assertFalse(professional.active());
        assertEquals(newCityId, professional.cityId());
        assertFalse(professional.updatedAt().isBefore(professional.createdAt()));

        assertEquals(1, professional.domainEvents().size());
        ProfessionalUpdatedEvent event = assertInstanceOf(
                ProfessionalUpdatedEvent.class, professional.domainEvents().getFirst());
        assertEquals("87654321", event.documentNumber());
        assertEquals("Maria", event.firstName());
        assertEquals("Gomez", event.lastName());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        professional.clearDomainEvents();

        assertThrows(InvalidProfessionalException.class,
                () -> professional.update(documentTypeId, "  ", "Juan", "Perez", professionalTypeId, "LIC123", true, cityId));
        assertTrue(professional.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        professional.clearDomainEvents();

        professional.delete();

        assertEquals(1, professional.domainEvents().size());
        ProfessionalDeletedEvent event = assertInstanceOf(
                ProfessionalDeletedEvent.class, professional.domainEvents().getLast());
        assertEquals(professional.id(), event.id());
    }
}