package com.mindconnect.application.referencedata.gender.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.gender.command.RegisterGenderCommand;
import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.application.referencedata.gender.exception.GenderAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.gender.exception.InvalidGenderException;
import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;

class RegisterGenderUseCaseTest {

    @Test
    void shouldRegisterAndPersistGender() {
        FakeGenderRepository repository = new FakeGenderRepository();
        RegisterGenderUseCase useCase = new RegisterGenderUseCase(repository);

        GenderResponse response = useCase.execute(new RegisterGenderCommand("Male"));

        assertNotNull(response.id());
        assertEquals("Male", response.description());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedDescription() {
        FakeGenderRepository repository = new FakeGenderRepository().with(Gender.register("Male"));
        RegisterGenderUseCase useCase = new RegisterGenderUseCase(repository);

        assertThrows(GenderAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterGenderCommand("  Male  ")));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterGenderUseCase useCase = new RegisterGenderUseCase(new FakeGenderRepository());

        assertThrows(InvalidGenderException.class,
                () -> useCase.execute(new RegisterGenderCommand("  ")));
    }
}