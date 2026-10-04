package com.mindconnect.domain.referencedata.gender.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.referencedata.gender.event.GenderDeletedEvent;
import com.mindconnect.domain.referencedata.gender.event.GenderRegisteredEvent;
import com.mindconnect.domain.referencedata.gender.event.GenderUpdatedEvent;
import com.mindconnect.domain.referencedata.gender.exception.InvalidGenderException;

class GenderTest {

    @Test
    void shouldRegisterGenderAndRecordEvent() {
        Gender gender = Gender.register("Male");

        assertNotNull(gender.id());
        assertEquals("Male", gender.description());
        assertEquals(gender.createdAt(), gender.updatedAt());

        assertEquals(1, gender.domainEvents().size());
        GenderRegisteredEvent event = assertInstanceOf(
                GenderRegisteredEvent.class, gender.domainEvents().getFirst());
        assertEquals(gender.id(), event.id());
    }

    @Test
    void shouldTrimDescriptionAndRejectBlank() {
        Gender gender = Gender.register("  Female  ");

        assertEquals("Female", gender.description());
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(InvalidGenderException.class, () -> Gender.register("  "));
    }

    @Test
    void shouldRejectNullDescription() {
        assertThrows(InvalidGenderException.class, () -> Gender.register(null));
    }

    @Test
    void shouldRejectTooLongDescription() {
        assertThrows(InvalidGenderException.class,
                () -> Gender.register("x".repeat(Gender.DESCRIPTION_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateDescriptionAndRecordEvent() {
        Gender gender = Gender.register("Male");
        gender.clearDomainEvents();

        gender.update("Female");

        assertEquals("Female", gender.description());
        assertFalse(gender.updatedAt().isBefore(gender.createdAt()));

        assertEquals(1, gender.domainEvents().size());
        GenderUpdatedEvent event = assertInstanceOf(
                GenderUpdatedEvent.class, gender.domainEvents().getFirst());
        assertEquals("Female", event.description());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        Gender gender = Gender.register("Male");
        gender.clearDomainEvents();

        assertThrows(InvalidGenderException.class, () -> gender.update(""));
        assertTrue(gender.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        Gender gender = Gender.register("Male");
        gender.clearDomainEvents();

        gender.delete();

        assertEquals(1, gender.domainEvents().size());
        GenderDeletedEvent event = assertInstanceOf(
                GenderDeletedEvent.class, gender.domainEvents().getLast());
        assertEquals(gender.id(), event.id());
    }
}