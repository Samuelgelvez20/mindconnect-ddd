package com.mindconnect.domain.professional.study.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.professional.study.event.StudyDeletedEvent;
import com.mindconnect.domain.professional.study.event.StudyRegisteredEvent;
import com.mindconnect.domain.professional.study.event.StudyUpdatedEvent;
import com.mindconnect.domain.professional.study.exception.InvalidStudyException;

class StudyTest {

    @Test
    void shouldRegisterStudyAndRecordEvent() {
        Study study = Study.register("Psychology");

        assertNotNull(study.id());
        assertEquals("Psychology", study.name());
        assertEquals(study.createdAt(), study.updatedAt());

        assertEquals(1, study.domainEvents().size());
        StudyRegisteredEvent event = assertInstanceOf(
                StudyRegisteredEvent.class, study.domainEvents().getFirst());
        assertEquals(study.id(), event.id());
    }

    @Test
    void shouldTrimName() {
        Study study = Study.register("  Psychology  ");

        assertEquals("Psychology", study.name());
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(InvalidStudyException.class, () -> Study.register("  "));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(InvalidStudyException.class, () -> Study.register(null));
    }

    @Test
    void shouldRejectTooLongName() {
        assertThrows(InvalidStudyException.class,
                () -> Study.register("x".repeat(Study.NAME_MAX_LENGTH + 1)));
    }

    @Test
    void shouldUpdateNameAndRecordEvent() {
        Study study = Study.register("Psychology");
        study.clearDomainEvents();

        study.update("Psychiatry");

        assertEquals("Psychiatry", study.name());
        assertFalse(study.updatedAt().isBefore(study.createdAt()));

        assertEquals(1, study.domainEvents().size());
        StudyUpdatedEvent event = assertInstanceOf(
                StudyUpdatedEvent.class, study.domainEvents().getFirst());
        assertEquals("Psychiatry", event.name());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        Study study = Study.register("Psychology");
        study.clearDomainEvents();

        assertThrows(InvalidStudyException.class, () -> study.update(""));
        assertTrue(study.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        Study study = Study.register("Psychology");
        study.clearDomainEvents();

        study.delete();

        assertEquals(1, study.domainEvents().size());
        StudyDeletedEvent event = assertInstanceOf(
                StudyDeletedEvent.class, study.domainEvents().getLast());
        assertEquals(study.id(), event.id());
    }
}