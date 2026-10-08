package com.mindconnect.application.professional.professionalstudy.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professionalstudy.command.RegisterProfessionalStudyCommand;
import com.mindconnect.application.professional.professionalstudy.dto.ProfessionalStudyResponse;
import com.mindconnect.domain.professional.professionalstudy.exception.InvalidProfessionalStudyException;
import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

class RegisterProfessionalStudyUseCaseTest {

    @Test
    void shouldRegisterAndPersistProfessionalStudy() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        FakeProfessionalStudyRepository repository = new FakeProfessionalStudyRepository();
        RegisterProfessionalStudyUseCase useCase = new RegisterProfessionalStudyUseCase(repository);

        ProfessionalStudyResponse response = useCase.execute(
                new RegisterProfessionalStudyCommand(studyId, professionalId, "Master in Psychology",
                        "University of Example", countryId));

        assertNotNull(response.id());
        assertEquals(studyId.value(), response.studyId());
        assertEquals(professionalId.value(), response.professionalId());
        assertEquals("Master in Psychology", response.title());
        assertEquals("University of Example", response.university());
        assertFalse(response.isValid());
        assertNull(response.resolutionNumber());
        assertEquals(countryId.value(), response.countryId());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterProfessionalStudyUseCase useCase = new RegisterProfessionalStudyUseCase(new FakeProfessionalStudyRepository());

        assertThrows(InvalidProfessionalStudyException.class,
                () -> useCase.execute(new RegisterProfessionalStudyCommand(
                        StudyId.generate(), ProfessionalId.generate(), "  ", "University", CountryId.generate())));
    }
}