package com.mindconnect.domain.clinicalcatalog.medicationroute;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.clinicalcatalog.medicationroute.event.MedicationRouteDeletedEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.event.MedicationRouteRegisteredEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.event.MedicationRouteUpdatedEvent;
import com.mindconnect.domain.clinicalcatalog.medicationroute.exception.InvalidMedicationRouteException;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicationRouteTest {

    @Test
    void register_ValidCodeAndName_CreatesRouteWithEvents() {
        String code = "ORAL";
        String name = "Oral Route";

        MedicationRoute route = MedicationRoute.register(code, name);

        assertNotNull(route.id());
        assertEquals(code, route.code());
        assertEquals(name, route.name());
        assertTrue(route.isActive());
        assertNotNull(route.createdAt());
        assertNotNull(route.updatedAt());

        var events = route.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof MedicationRouteRegisteredEvent);
    }

    @Test
    void register_CodeIsBlank_ThrowsException() {
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register(" ", "name"));
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register("", "name"));
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register(null, "name"));
    }

    @Test
    void register_NameIsBlank_ThrowsException() {
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register("code", " "));
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register("code", ""));
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register("code", null));
    }

    @Test
    void register_CodeTooLong_ThrowsException() {
        String longCode = "a".repeat(21);
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register(longCode, "name"));
    }

    @Test
    void register_NameTooLong_ThrowsException() {
        String longName = "a".repeat(51);
        assertThrows(InvalidMedicationRouteException.class, () -> MedicationRoute.register("code", longName));
    }

    @Test
    void restore_CreatesRouteWithoutEvents() {
        MedicationRouteId id = MedicationRouteId.generate();
        Instant now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);

        MedicationRoute route = MedicationRoute.restore(id, "code", "name", true, now, now);

        assertEquals(id, route.id());
        assertEquals("code", route.code());
        assertEquals("name", route.name());
        assertTrue(route.isActive());
        assertEquals(now, route.createdAt());
        assertEquals(now, route.updatedAt());
        assertTrue(route.domainEvents().isEmpty());
    }

    @Test
    void update_ValidCodeAndName_UpdatesFieldsAndRecordsEvent() {
        MedicationRoute route = MedicationRoute.register("ORAL", "Oral Route");
        int initialEventCount = route.domainEvents().size();

        route.update("IV", "Intravenous", false);

        assertEquals("IV", route.code());
        assertEquals("Intravenous", route.name());
        assertFalse(route.isActive());
        assertNotNull(route.updatedAt());

        var events = route.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof MedicationRouteUpdatedEvent);
    }

    @Test
    void update_ActiveChange_UpdatesActiveField() {
        MedicationRoute route = MedicationRoute.register("ORAL", "Oral Route");
        assertTrue(route.isActive());

        route.update("ORAL", "Oral Route", false);

        assertFalse(route.isActive());
    }

    @Test
    void update_CodeIsBlank_ThrowsException() {
        MedicationRoute route = MedicationRoute.register("ORAL", "Oral Route");
        assertThrows(InvalidMedicationRouteException.class, () -> route.update(" ", "name", true));
    }

    @Test
    void update_NameIsBlank_ThrowsException() {
        MedicationRoute route = MedicationRoute.register("ORAL", "Oral Route");
        assertThrows(InvalidMedicationRouteException.class, () -> route.update("code", " ", true));
    }

    @Test
    void delete_RegistersDeletedEvent() {
        MedicationRoute route = MedicationRoute.register("ORAL", "Oral Route");
        int initialEventCount = route.domainEvents().size();

        route.delete();

        var events = route.domainEvents();
        assertEquals(initialEventCount + 1, events.size());
        assertTrue(events.get(events.size() - 1) instanceof MedicationRouteDeletedEvent);
    }
}