package com.mindconnect.domain.referencedata.citymunicipality.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityDeletedEvent;
import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityRegisteredEvent;
import com.mindconnect.domain.referencedata.citymunicipality.event.CityMunicipalityUpdatedEvent;
import com.mindconnect.domain.referencedata.citymunicipality.exception.InvalidCityMunicipalityException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

class CityMunicipalityTest {

    @Test
    void shouldRegisterActiveCityMunicipalityAndRecordEvent() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", "Ciudad de la eterna primavera", regionId);

        assertNotNull(cityMunicipality.id());
        assertEquals("Medellín", cityMunicipality.name());
        assertEquals("MED", cityMunicipality.code());
        assertEquals("Ciudad de la eterna primavera", cityMunicipality.description());
        assertEquals(regionId, cityMunicipality.regionId());
        assertTrue(cityMunicipality.active());
        assertEquals(cityMunicipality.createdAt(), cityMunicipality.updatedAt());

        assertEquals(1, cityMunicipality.domainEvents().size());
        CityMunicipalityRegisteredEvent event = assertInstanceOf(
                CityMunicipalityRegisteredEvent.class, cityMunicipality.domainEvents().getFirst());
        assertEquals(cityMunicipality.id(), event.id());
    }

    @Test
    void shouldTrimTextAndTurnBlankDescriptionIntoNull() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("  Medellín  ", " MED ", "   ", regionId);

        assertEquals("Medellín", cityMunicipality.name());
        assertEquals("MED", cityMunicipality.code());
        assertNull(cityMunicipality.description());
    }

    @Test
    void shouldRejectBlankName() {
        StateRegionId regionId = StateRegionId.generate();
        assertThrows(InvalidCityMunicipalityException.class, () -> CityMunicipality.register("  ", "MED", null, regionId));
    }

    @Test
    void shouldRejectNullCode() {
        StateRegionId regionId = StateRegionId.generate();
        assertThrows(InvalidCityMunicipalityException.class, () -> CityMunicipality.register("Medellín", null, null, regionId));
    }

    @Test
    void shouldRejectNullRegionId() {
        assertThrows(InvalidCityMunicipalityException.class, () -> CityMunicipality.register("Medellín", "MED", null, null));
    }

    @Test
    void shouldRejectTooLongValues() {
        StateRegionId regionId = StateRegionId.generate();
        assertThrows(InvalidCityMunicipalityException.class,
                () -> CityMunicipality.register("x".repeat(CityMunicipality.NAME_MAX_LENGTH + 1), "MED", null, regionId));
        assertThrows(InvalidCityMunicipalityException.class,
                () -> CityMunicipality.register("Medellín", "x".repeat(CityMunicipality.CODE_MAX_LENGTH + 1), null, regionId));
        assertThrows(InvalidCityMunicipalityException.class,
                () -> CityMunicipality.register("Medellín", "MED", "x".repeat(CityMunicipality.DESCRIPTION_MAX_LENGTH + 1), regionId));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", null, regionId);
        cityMunicipality.clearDomainEvents();

        cityMunicipality.update("Bogotá", "BOG", "Capital de Colombia", false);

        assertEquals("Bogotá", cityMunicipality.name());
        assertEquals("BOG", cityMunicipality.code());
        assertEquals("Capital de Colombia", cityMunicipality.description());
        assertFalse(cityMunicipality.active());
        assertFalse(cityMunicipality.updatedAt().isBefore(cityMunicipality.createdAt()));

        assertEquals(1, cityMunicipality.domainEvents().size());
        CityMunicipalityUpdatedEvent event = assertInstanceOf(
                CityMunicipalityUpdatedEvent.class, cityMunicipality.domainEvents().getFirst());
        assertEquals("BOG", event.code());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", null, regionId);
        cityMunicipality.clearDomainEvents();

        assertThrows(InvalidCityMunicipalityException.class, () -> cityMunicipality.update("", "BOG", null, true));
        assertTrue(cityMunicipality.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        StateRegionId regionId = StateRegionId.generate();
        CityMunicipality cityMunicipality = CityMunicipality.register("Medellín", "MED", null, regionId);
        cityMunicipality.clearDomainEvents();

        cityMunicipality.delete();

        assertEquals(1, cityMunicipality.domainEvents().size());
        CityMunicipalityDeletedEvent event = assertInstanceOf(
                CityMunicipalityDeletedEvent.class, cityMunicipality.domainEvents().getLast());
        assertEquals(cityMunicipality.id(), event.id());
    }
}