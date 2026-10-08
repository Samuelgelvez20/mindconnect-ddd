package com.mindconnect.application.professional.professionalstudy.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professionalstudy.command.UpdateProfessionalStudyCommand;
import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.application.professional.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

class UpdateProfessionalStudyUseCaseTest {

    @Test
    void shouldUpdateExistingProfessionalStudy() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master in Psychology", "University of Example", countryId);
        FakeProfessionalStudyRepository repository = new FakeProfessionalStudyRepository().with(professionalStudy);
        UpdateProfessionalStudyUseCase useCase = new UpdateProfessionalStudyUseCase(repository);

        ProfessionalStudyResponse response = useCase.execute(
                new UpdateProfessionalStudyCommand(professionalStudy.id(), "PhD in Psychology",
                        "University of Another", true, "RES-123", countryId));

        assertEquals("PhD in Psychology", response.title());
        assertEquals("University of Another", response.university());
        assertTrue(response.isValid());
        assertEquals("RES-123", response.resolutionNumber());
    }

    @Test
    void shouldRejectWhenProfessionalStudyDoesNotExist() {
        UpdateProfessionalStudyUseCase useCase = new UpdateProfessionalStudyUseCase(new FakeProfessionalStudyRepository());

        assertThrows(ProfessionalStudyNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateProfessionalStudyCommand(ProfessionalStudyId.generate(),
                                "PhD", "University", true, "RES-123", CountryId.generate())));
    }
}