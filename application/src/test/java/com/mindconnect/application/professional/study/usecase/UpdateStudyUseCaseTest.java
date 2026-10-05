package com.mindconnect.application.professional.study.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.study.command.UpdateStudyCommand;
import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.application.professional.study.exception.StudyNotFoundApplicationException;
import com.mindconnect.domain.professional.study.model.aggregate.Study;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

class UpdateStudyUseCaseTest {

    @Test
    void shouldUpdateExistingStudy() {
        Study study = Study.register("Psychology");
        FakeStudyRepository repository = new FakeStudyRepository().with(study);
        UpdateStudyUseCase useCase = new UpdateStudyUseCase(repository);

        StudyResponse response = useCase.execute(
                new UpdateStudyCommand(study.id(), "Psychiatry"));

        assertEquals("Psychiatry", response.name());
    }

    @Test
    void shouldRejectWhenStudyDoesNotExist() {
        UpdateStudyUseCase useCase = new UpdateStudyUseCase(new FakeStudyRepository());

        assertThrows(StudyNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateStudyCommand(StudyId.generate(), "Psychiatry")));
    }
}