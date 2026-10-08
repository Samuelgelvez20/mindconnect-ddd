package com.mindconnect.application.professional.study.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.study.command.RegisterStudyCommand;
import com.mindconnect.application.professional.study.dto.StudyResponse;
import com.mindconnect.domain.professional.study.exception.InvalidStudyException;
import com.mindconnect.domain.professional.study.model.aggregate.Study;

class RegisterStudyUseCaseTest {

    @Test
    void shouldRegisterAndPersistStudy() {
        FakeStudyRepository repository = new FakeStudyRepository();
        RegisterStudyUseCase useCase = new RegisterStudyUseCase(repository);

        StudyResponse response = useCase.execute(new RegisterStudyCommand("Psychology"));

        assertNotNull(response.id());
        assertEquals("Psychology", response.name());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterStudyUseCase useCase = new RegisterStudyUseCase(new FakeStudyRepository());

        assertThrows(InvalidStudyException.class,
                () -> useCase.execute(new RegisterStudyCommand("  ")));
    }
}