package com.mindconnect.application.professional.study.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.study.exception.StudyNotFoundApplicationException;
import com.mindconnect.domain.professional.study.event.StudyDeletedEvent;
import com.mindconnect.domain.professional.study.model.aggregate.Study;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

class DeleteStudyUseCaseTest {

    @Test
    void shouldDeleteExistingStudyAndRecordEvent() {
        Study study = Study.register("Psychology");
        FakeStudyRepository repository = new FakeStudyRepository().with(study);
        DeleteStudyUseCase useCase = new DeleteStudyUseCase(repository);

        useCase.execute(study.id());

        assertEquals(1, repository.deleted().size());
        assertSame(study, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(StudyDeletedEvent.class, study.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenStudyDoesNotExist() {
        FakeStudyRepository repository = new FakeStudyRepository();
        DeleteStudyUseCase useCase = new DeleteStudyUseCase(repository);

        assertThrows(StudyNotFoundApplicationException.class,
                () -> useCase.execute(StudyId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}