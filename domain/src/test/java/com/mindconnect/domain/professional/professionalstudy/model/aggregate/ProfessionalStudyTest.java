package com.mindconnect.domain.professional.professionalstudy.model.aggregate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyDeletedEvent;
import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyRegisteredEvent;
import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyUpdatedEvent;
import com.mindconnect.domain.professional.professionalstudy.exception.InvalidProfessionalStudyException;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

class ProfessionalStudyTest {

    @Test
    void shouldRegisterProfessionalStudyAndRecordEvent() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master in Psychology", "University of Example", countryId);

        assertNotNull(professionalStudy.id());
        assertEquals(studyId, professionalStudy.studyId());
        assertEquals(professionalId, professionalStudy.professionalId());
        assertEquals("Master in Psychology", professionalStudy.title());
        assertEquals("University of Example", professionalStudy.university());
        assertFalse(professionalStudy.isValid());
        assertNull(professionalStudy.resolutionNumber());
        assertEquals(countryId, professionalStudy.countryId());
        assertEquals(professionalStudy.createdAt(), professionalStudy.updatedAt());

        assertEquals(1, professionalStudy.domainEvents().size());
        ProfessionalStudyRegisteredEvent event = assertInstanceOf(
                ProfessionalStudyRegisteredEvent.class, professionalStudy.domainEvents().getFirst());
        assertEquals(professionalStudy.id(), event.id());
    }

    @Test
    void shouldTrimTitleAndUniversity() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "  Master in Psychology  ", "  University of Example  ", countryId);

        assertEquals("Master in Psychology", professionalStudy.title());
        assertEquals("University of Example", professionalStudy.university());
    }

    @Test
    void shouldRejectBlankTitle() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId, "  ", "University", countryId));
    }

    @Test
    void shouldRejectNullTitle() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId, null, "University", countryId));
    }

    @Test
    void shouldRejectBlankUniversity() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId, "Master", "  ", countryId));
    }

    @Test
    void shouldRejectNullUniversity() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId, "Master", null, countryId));
    }

    @Test
    void shouldRejectTooLongTitle() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId,
                        "x".repeat(ProfessionalStudy.TITLE_MAX_LENGTH + 1), "University", countryId));
    }

    @Test
    void shouldRejectTooLongUniversity() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId, "Master",
                        "x".repeat(ProfessionalStudy.UNIVERSITY_MAX_LENGTH + 1), countryId));
    }

    @Test
    void shouldRejectNullStudyId() {
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(null, professionalId, "Master", "University", countryId));
    }

    @Test
    void shouldRejectNullProfessionalId() {
        StudyId studyId = StudyId.generate();
        CountryId countryId = CountryId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, null, "Master", "University", countryId));
    }

    @Test
    void shouldRejectNullCountryId() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> ProfessionalStudy.register(studyId, professionalId, "Master", "University", null));
    }

    @Test
    void shouldUpdateFieldsAndRecordEvent() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master in Psychology", "University of Example", countryId);
        professionalStudy.clearDomainEvents();

        CountryId newCountryId = CountryId.generate();

        professionalStudy.update(
                "PhD in Psychology",
                "University of Another Example",
                true,
                "RES-12345",
                newCountryId);

        assertEquals("PhD in Psychology", professionalStudy.title());
        assertEquals("University of Another Example", professionalStudy.university());
        assertTrue(professionalStudy.isValid());
        assertEquals("RES-12345", professionalStudy.resolutionNumber());
        assertEquals(newCountryId, professionalStudy.countryId());
        assertFalse(professionalStudy.updatedAt().isBefore(professionalStudy.createdAt()));

        assertEquals(1, professionalStudy.domainEvents().size());
        ProfessionalStudyUpdatedEvent event = assertInstanceOf(
                ProfessionalStudyUpdatedEvent.class, professionalStudy.domainEvents().getFirst());
        assertEquals("PhD in Psychology", event.title());
        assertEquals("University of Another Example", event.university());
    }

    @Test
    void shouldTurnBlankResolutionNumberIntoNull() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master", "University", countryId);
        professionalStudy.clearDomainEvents();

        professionalStudy.update("Master", "University", true, "  ", countryId);

        assertNull(professionalStudy.resolutionNumber());
    }

    @Test
    void shouldKeepStateWhenUpdateIsInvalid() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master", "University", countryId);
        professionalStudy.clearDomainEvents();

        assertThrows(InvalidProfessionalStudyException.class,
                () -> professionalStudy.update("", "University", true, null, countryId));
        assertTrue(professionalStudy.domainEvents().isEmpty());
    }

    @Test
    void shouldRecordDeletedEventOnDelete() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master", "University", countryId);
        professionalStudy.clearDomainEvents();

        professionalStudy.delete();

        assertEquals(1, professionalStudy.domainEvents().size());
        ProfessionalStudyDeletedEvent event = assertInstanceOf(
                ProfessionalStudyDeletedEvent.class, professionalStudy.domainEvents().getLast());
        assertEquals(professionalStudy.id(), event.id());
    }
}