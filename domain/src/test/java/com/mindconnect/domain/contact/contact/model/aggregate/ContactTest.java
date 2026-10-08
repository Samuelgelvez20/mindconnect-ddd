package com.mindconnect.domain.contact.contact.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.contact.contact.event.ContactDeletedEvent;
import com.mindconnect.domain.contact.contact.event.ContactRegisteredEvent;
import com.mindconnect.domain.contact.contact.event.ContactUpdatedEvent;
import com.mindconnect.domain.contact.contact.exception.InvalidContactException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

class ContactTest {

    @Test
    void shouldRegisterContactAndRecordEvent() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "Juan Carlos Perez Gomez",
                "juan@example.com",
                "Some notes",
                cityId, createdBy);

        assertNotNull(contact.id());
        assertEquals("Juan Carlos Perez Gomez", contact.fullName());
        assertEquals("juan@example.com", contact.email());
        assertEquals("Some notes", contact.notes());
        assertEquals(cityId, contact.cityId());
        assertEquals(createdBy, contact.createdBy());
        assertNull(contact.updatedBy());
        assertEquals(contact.createdAt(), contact.updatedAt());

        assertEquals(1, contact.domainEvents().size());
        ContactRegisteredEvent event = assertInstanceOf(
                ContactRegisteredEvent.class, contact.domainEvents().getFirst());
        assertEquals(contact.id(), event.id());
    }

    @Test
    void shouldRegisterContactWithOptionalFieldsNull() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "Juan Carlos Perez Gomez",
                null,
                null,
                cityId, createdBy);

        assertEquals("Juan Carlos Perez Gomez", contact.fullName());
        assertNull(contact.email());
        assertNull(contact.notes());
    }

    @Test
    void shouldTrimTextAndNullifyOptionalBlanks() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "  Juan Carlos Perez Gomez  ",
                "  juan.perez@example.com  ",
                "   ",
                cityId, createdBy);

        assertEquals("Juan Carlos Perez Gomez", contact.fullName());
        assertEquals("juan.perez@example.com", contact.email());
        assertNull(contact.notes());
    }

    @Test
    void shouldRejectBlankFullName() {
        assertThrows(InvalidContactException.class, () -> Contact.register(
                "  ", "juan@example.com", null,
                CityMunicipalityId.generate(), ProfessionalId.generate()));
    }

    @Test
    void shouldRejectNullFullName() {
        assertThrows(InvalidContactException.class, () -> Contact.register(
                null, "juan@example.com", null,
                CityMunicipalityId.generate(), ProfessionalId.generate()));
    }

    @Test
    void shouldRejectTooLongFullName() {
        assertThrows(InvalidContactException.class,
                () -> Contact.register("x".repeat(Contact.FULL_NAME_MAX_LENGTH + 1), "juan@example.com", null,
                        CityMunicipalityId.generate(), ProfessionalId.generate()));
    }

    @Test
    void shouldRejectTooLongEmail() {
        assertThrows(InvalidContactException.class,
                () -> Contact.register("Juan Perez", "x".repeat(Contact.EMAIL_MAX_LENGTH + 1) + "@example.com", null,
                        CityMunicipalityId.generate(), ProfessionalId.generate()));
    }

    @Test
    void shouldRejectNullCityId() {
        assertThrows(InvalidContactException.class, () -> Contact.register(
                "Juan Perez", "juan@example.com", null,
                null, ProfessionalId.generate()));
    }

    @Test
    void shouldRejectNullCreatedBy() {
        assertThrows(InvalidContactException.class, () -> Contact.register(
                "Juan Perez", "juan@example.com", null,
                CityMunicipalityId.generate(), null));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "Juan Carlos Perez Gomez",
                "juan.perez@example.com", "Calle 123",
                cityId, createdBy);
        contact.clearDomainEvents();

        CityMunicipalityId newCityId = CityMunicipalityId.generate();
        ProfessionalId updatedBy = ProfessionalId.generate();

        contact.update(
                "Maria Gomez Lopez",
                "maria.lopez@example.com",
                "Carrera 45 #67-89",
                newCityId, updatedBy);

        assertEquals("Maria Gomez Lopez", contact.fullName());
        assertEquals("maria.lopez@example.com", contact.email());
        assertEquals("Carrera 45 #67-89", contact.notes());
        assertEquals(newCityId, contact.cityId());
        assertEquals(updatedBy, contact.updatedBy());
        assertFalse(contact.updatedAt().isBefore(contact.createdAt()));

        assertEquals(1, contact.domainEvents().size());
        ContactUpdatedEvent event = assertInstanceOf(
                ContactUpdatedEvent.class, contact.domainEvents().getFirst());
        assertEquals("Maria Gomez Lopez", event.fullName());
        assertEquals("maria.lopez@example.com", event.email());
    }

    @Test
    void shouldUpdateWithOptionalFieldsNull() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "Juan Carlos Perez Gomez",
                "juan.perez@example.com", "Calle 123",
                cityId, createdBy);
        contact.clearDomainEvents();

        CityMunicipalityId newCityId = CityMunicipalityId.generate();
        ProfessionalId updatedBy = ProfessionalId.generate();

        contact.update(
                "Maria Gomez Lopez",
                null,
                null,
                newCityId, updatedBy);

        assertEquals("Maria Gomez Lopez", contact.fullName());
        assertNull(contact.email());
        assertNull(contact.notes());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "Juan Carlos Perez Gomez",
                "juan.perez@example.com", "Calle 123",
                cityId, createdBy);
        contact.clearDomainEvents();

        assertThrows(InvalidContactException.class, () -> contact.update(
                "", "maria.lopez@example.com", "Carrera 45",
                CityMunicipalityId.generate(), ProfessionalId.generate()));
        assertTrue(contact.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();

        Contact contact = Contact.register(
                "Juan Carlos Perez Gomez",
                "juan.perez@example.com", "Calle 123",
                cityId, createdBy);
        contact.clearDomainEvents();

        contact.delete();

        assertEquals(1, contact.domainEvents().size());
        ContactDeletedEvent event = assertInstanceOf(
                ContactDeletedEvent.class, contact.domainEvents().getLast());
        assertEquals(contact.id(), event.id());
    }

    @Test
    void shouldRestoreContactFromPersistence() {
        ContactId id = ContactId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        ProfessionalId updatedBy = ProfessionalId.generate();
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now().plusSeconds(100);

        Contact contact = Contact.restore(
                id,
                "Juan Carlos Perez Gomez",
                "juan.perez@example.com",
                "Some notes",
                cityId,
                createdBy,
                updatedBy,
                createdAt,
                updatedAt);

        assertEquals(id, contact.id());
        assertEquals("Juan Carlos Perez Gomez", contact.fullName());
        assertEquals("juan.perez@example.com", contact.email());
        assertEquals("Some notes", contact.notes());
        assertEquals(cityId, contact.cityId());
        assertEquals(createdBy, contact.createdBy());
        assertEquals(updatedBy, contact.updatedBy());
        assertEquals(createdAt, contact.createdAt());
        assertEquals(updatedAt, contact.updatedAt());
        assertTrue(contact.domainEvents().isEmpty());
    }
}