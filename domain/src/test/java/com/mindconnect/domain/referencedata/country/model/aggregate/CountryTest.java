package com.mindconnect.domain.referencedata.country.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.referencedata.country.event.CountryDeletedEvent;
import com.mindconnect.domain.referencedata.country.event.CountryRegisteredEvent;
import com.mindconnect.domain.referencedata.country.event.CountryUpdatedEvent;
import com.mindconnect.domain.referencedata.country.exception.InvalidCountryException;

class CountryTest {

    @Test
    void shouldRegisterActiveCountryAndRecordEvent() {
        Country country = Country.register("Colombia", "CO", "South America", "57");

        assertNotNull(country.id());
        assertEquals("Colombia", country.name());
        assertEquals("CO", country.code());
        assertEquals("South America", country.description());
        assertEquals("57", country.telephonePrefix());
        assertTrue(country.active());
        assertEquals(country.createdAt(), country.updatedAt());

        assertEquals(1, country.domainEvents().size());
        CountryRegisteredEvent event = assertInstanceOf(
                CountryRegisteredEvent.class, country.domainEvents().getFirst());
        assertEquals(country.id(), event.id());
    }

    @Test
    void shouldTrimTextAndTurnBlankOptionalsIntoNull() {
        Country country = Country.register("  Colombia ", " CO ", "   ", "");

        assertEquals("Colombia", country.name());
        assertEquals("CO", country.code());
        assertNull(country.description());
        assertNull(country.telephonePrefix());
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(InvalidCountryException.class, () -> Country.register("  ", "CO", null, null));
    }

    @Test
    void shouldRejectNullCode() {
        assertThrows(InvalidCountryException.class, () -> Country.register("Colombia", null, null, null));
    }

    @Test
    void shouldRejectTooLongValues() {
        assertThrows(InvalidCountryException.class,
                () -> Country.register("x".repeat(Country.NAME_MAX_LENGTH + 1), "CO", null, null));
        assertThrows(InvalidCountryException.class,
                () -> Country.register("Colombia", "x".repeat(Country.CODE_MAX_LENGTH + 1), null, null));
        assertThrows(InvalidCountryException.class,
                () -> Country.register("Colombia", "CO", "x".repeat(Country.DESCRIPTION_MAX_LENGTH + 1), null));
        assertThrows(InvalidCountryException.class,
                () -> Country.register("Colombia", "CO", null, "x".repeat(Country.TELEPHONE_PREFIX_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        Country country = Country.register("Colombia", "CO", null, null);
        country.clearDomainEvents();

        country.update("Peru", "PE", "Andean", "51", false);

        assertEquals("Peru", country.name());
        assertEquals("PE", country.code());
        assertEquals("Andean", country.description());
        assertEquals("51", country.telephonePrefix());
        assertFalse(country.active());
        assertFalse(country.updatedAt().isBefore(country.createdAt()));

        assertEquals(1, country.domainEvents().size());
        CountryUpdatedEvent event = assertInstanceOf(
                CountryUpdatedEvent.class, country.domainEvents().getFirst());
        assertEquals("PE", event.code());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        Country country = Country.register("Colombia", "CO", null, null);
        country.clearDomainEvents();

        assertThrows(InvalidCountryException.class, () -> country.update("", "PE", null, null, true));
        assertTrue(country.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        Country country = Country.register("Colombia", "CO", null, null);
        country.clearDomainEvents();

        country.delete();

        assertEquals(1, country.domainEvents().size());
        CountryDeletedEvent event = assertInstanceOf(
                CountryDeletedEvent.class, country.domainEvents().getFirst());
        assertEquals(country.id(), event.id());
    }
}
