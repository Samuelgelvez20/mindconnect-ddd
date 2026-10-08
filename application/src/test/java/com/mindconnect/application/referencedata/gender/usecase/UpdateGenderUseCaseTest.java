package com.mindconnect.application.referencedata.gender.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.gender.command.UpdateGenderCommand;
import com.mindconnect.application.referencedata.gender.dto.GenderResponse;
import com.mindconnect.application.referencedata.gender.exception.GenderAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.gender.exception.GenderNotFoundApplicationException;
import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

class UpdateGenderUseCaseTest {

    @Test
    void shouldUpdateExistingGender() {
        Gender gender = Gender.register("Male");
        FakeGenderRepository repository = new FakeGenderRepository().with(gender);
        UpdateGenderUseCase useCase = new UpdateGenderUseCase(repository);

        GenderResponse response = useCase.execute(
                new UpdateGenderCommand(gender.id(), "Female"));

        assertEquals("Female", response.description());
    }

    @Test
    void shouldAllowKeepingTheSameDescription() {
        Gender gender = Gender.register("Male");
        UpdateGenderUseCase useCase =
                new UpdateGenderUseCase(new FakeGenderRepository().with(gender));

        GenderResponse response = useCase.execute(
                new UpdateGenderCommand(gender.id(), "Male"));

        assertEquals("Male", response.description());
    }

    @Test
    void shouldRejectWhenGenderDoesNotExist() {
        UpdateGenderUseCase useCase = new UpdateGenderUseCase(new FakeGenderRepository());

        assertThrows(GenderNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateGenderCommand(GenderId.generate(), "Female")));
    }

    @Test
    void shouldRejectDescriptionUsedByAnotherGender() {
        Gender male = Gender.register("Male");
        Gender female = Gender.register("Female");
        UpdateGenderUseCase useCase =
                new UpdateGenderUseCase(new FakeGenderRepository().with(male, female));

        assertThrows(GenderAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateGenderCommand(female.id(), "Male")));
    }
}