package com.mindconnect.application.professional.professionalstudy.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyDeletedEvent;
import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

class DeleteProfessionalStudyUseCaseTest {

    @Test
    void shouldDeleteExistingProfessionalStudyAndRecordEvent() {
        StudyId studyId = StudyId.generate();
        ProfessionalId professionalId = ProfessionalId.generate();
        CountryId countryId = CountryId.generate();

        ProfessionalStudy professionalStudy = ProfessionalStudy.register(
                studyId, professionalId, "Master in Psychology", "University of Example", countryId);
        FakeProfessionalStudyRepository repository = new FakeProfessionalStudyRepository().with(professionalStudy);
        DeleteProfessionalStudyUseCase useCase = new DeleteProfessionalStudyUseCase(repository);

        useCase.execute(professionalStudy.id());

        assertEquals(1, repository.deleted().size());
        assertSame(professionalStudy, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(ProfessionalStudyDeletedEvent.class, professionalStudy.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenProfessionalStudyDoesNotExist() {
        FakeProfessionalStudyRepository repository = new FakeProfessionalStudyRepository();
        DeleteProfessionalStudyUseCase useCase = new DeleteProfessionalStudyUseCase(repository);

        assertThrows(ProfessionalStudyNotFoundApplicationException.class,
                () -> useCase.execute(ProfessionalStudyId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}