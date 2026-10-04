package com.mindconnect.domain.referencedata.stateregion.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionDeletedEvent;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionRegisteredEvent;
import com.mindconnect.domain.referencedata.stateregion.event.StateRegionUpdatedEvent;
import com.mindconnect.domain.referencedata.stateregion.exception.InvalidStateRegionException;

class StateRegionTest {

    @Test
    void shouldRegisterActiveStateRegionAndRecordEvent() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", "Departamento de Antioquia", countryId);

        assertNotNull(stateRegion.id());
        assertEquals("Antioquia", stateRegion.name());
        assertEquals("ANT", stateRegion.code());
        assertEquals("Departamento de Antioquia", stateRegion.description());
        assertEquals(countryId, stateRegion.countryId());
        assertTrue(stateRegion.active());
        assertEquals(stateRegion.createdAt(), stateRegion.updatedAt());

        assertEquals(1, stateRegion.domainEvents().size());
        StateRegionRegisteredEvent event = assertInstanceOf(
                StateRegionRegisteredEvent.class, stateRegion.domainEvents().getFirst());
        assertEquals(stateRegion.id(), event.id());
    }

    @Test
    void shouldTrimTextAndTurnBlankDescriptionIntoNull() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("  Antioquia  ", " ANT ", "   ", countryId);

        assertEquals("Antioquia", stateRegion.name());
        assertEquals("ANT", stateRegion.code());
        assertNull(stateRegion.description());
    }

    @Test
    void shouldRejectBlankName() {
        CountryId countryId = CountryId.generate();
        assertThrows(InvalidStateRegionException.class, () -> StateRegion.register("  ", "ANT", null, countryId));
    }

    @Test
    void shouldRejectNullCode() {
        CountryId countryId = CountryId.generate();
        assertThrows(InvalidStateRegionException.class, () -> StateRegion.register("Antioquia", null, null, countryId));
    }

    @Test
    void shouldRejectNullCountryId() {
        assertThrows(InvalidStateRegionException.class, () -> StateRegion.register("Antioquia", "ANT", null, null));
    }

    @Test
    void shouldRejectTooLongValues() {
        CountryId countryId = CountryId.generate();
        assertThrows(InvalidStateRegionException.class,
                () -> StateRegion.register("x".repeat(StateRegion.NAME_MAX_LENGTH + 1), "ANT", null, countryId));
        assertThrows(InvalidStateRegionException.class,
                () -> StateRegion.register("Antioquia", "x".repeat(StateRegion.CODE_MAX_LENGTH + 1), null, countryId));
        assertThrows(InvalidStateRegionException.class,
                () -> StateRegion.register("Antioquia", "ANT", "x".repeat(StateRegion.DESCRIPTION_MAX_LENGTH + 1), countryId));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", null, countryId);
        stateRegion.clearDomainEvents();

        stateRegion.update("Cundinamarca", "CUN", "Departamento de Cundinamarca", false);

        assertEquals("Cundinamarca", stateRegion.name());
        assertEquals("CUN", stateRegion.code());
        assertEquals("Departamento de Cundinamarca", stateRegion.description());
        assertFalse(stateRegion.active());
        assertFalse(stateRegion.updatedAt().isBefore(stateRegion.createdAt()));

        assertEquals(1, stateRegion.domainEvents().size());
        StateRegionUpdatedEvent event = assertInstanceOf(
                StateRegionUpdatedEvent.class, stateRegion.domainEvents().getFirst());
        assertEquals("CUN", event.code());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", null, countryId);
        stateRegion.clearDomainEvents();

        assertThrows(InvalidStateRegionException.class, () -> stateRegion.update("", "CUN", null, true));
        assertTrue(stateRegion.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        CountryId countryId = CountryId.generate();
        StateRegion stateRegion = StateRegion.register("Antioquia", "ANT", null, countryId);
        stateRegion.clearDomainEvents();

        stateRegion.delete();

        assertEquals(1, stateRegion.domainEvents().size());
        StateRegionDeletedEvent event = assertInstanceOf(
                StateRegionDeletedEvent.class, stateRegion.domainEvents().getLast());
        assertEquals(stateRegion.id(), event.id());
    }
}