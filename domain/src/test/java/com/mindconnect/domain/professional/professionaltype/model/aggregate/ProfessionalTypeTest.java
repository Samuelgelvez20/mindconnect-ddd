package com.mindconnect.domain.professional.professionaltype.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeDeletedEvent;
import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeRegisteredEvent;
import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeUpdatedEvent;
import com.mindconnect.domain.professional.professionaltype.exception.InvalidProfessionalTypeException;

class ProfessionalTypeTest {

    @Test
    void shouldRegisterProfessionalTypeAndRecordEvent() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");

        assertNotNull(professionalType.id());
        assertEquals("Psychologist", professionalType.name());
        assertEquals(professionalType.createdAt(), professionalType.updatedAt());

        assertEquals(1, professionalType.domainEvents().size());
        ProfessionalTypeRegisteredEvent event = assertInstanceOf(
                ProfessionalTypeRegisteredEvent.class, professionalType.domainEvents().getFirst());
        assertEquals(professionalType.id(), event.id());
    }

    @Test
    void shouldTrimName() {
        ProfessionalType professionalType = ProfessionalType.register("  Psychologist  ");

        assertEquals("Psychologist", professionalType.name());
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(InvalidProfessionalTypeException.class, () -> ProfessionalType.register("  "));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(InvalidProfessionalTypeException.class, () -> ProfessionalType.register(null));
    }

    @Test
    void shouldRejectTooLongName() {
        assertThrows(InvalidProfessionalTypeException.class,
                () -> ProfessionalType.register("x".repeat(ProfessionalType.NAME_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateNameAndRecordEvent() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");
        professionalType.clearDomainEvents();

        professionalType.update("Psychiatrist");

        assertEquals("Psychiatrist", professionalType.name());
        assertFalse(professionalType.updatedAt().isBefore(professionalType.createdAt()));

        assertEquals(1, professionalType.domainEvents().size());
        ProfessionalTypeUpdatedEvent event = assertInstanceOf(
                ProfessionalTypeUpdatedEvent.class, professionalType.domainEvents().getFirst());
        assertEquals("Psychiatrist", event.name());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");
        professionalType.clearDomainEvents();

        assertThrows(InvalidProfessionalTypeException.class, () -> professionalType.update(""));
        assertTrue(professionalType.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");
        professionalType.clearDomainEvents();

        professionalType.delete();

        assertEquals(1, professionalType.domainEvents().size());
        ProfessionalTypeDeletedEvent event = assertInstanceOf(
                ProfessionalTypeDeletedEvent.class, professionalType.domainEvents().getLast());
        assertEquals(professionalType.id(), event.id());
    }
}