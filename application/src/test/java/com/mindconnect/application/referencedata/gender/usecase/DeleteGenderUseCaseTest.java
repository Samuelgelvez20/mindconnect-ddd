package com.mindconnect.application.referencedata.gender.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.gender.exception.GenderNotFoundApplicationException;
import com.mindconnect.domain.referencedata.gender.event.GenderDeletedEvent;
import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

class DeleteGenderUseCaseTest {

    @Test
    void shouldDeleteExistingGenderAndRecordEvent() {
        Gender gender = Gender.register("Male");
        FakeGenderRepository repository = new FakeGenderRepository().with(gender);
        DeleteGenderUseCase useCase = new DeleteGenderUseCase(repository);

        useCase.execute(gender.id());

        assertEquals(1, repository.deleted().size());
        assertSame(gender, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(GenderDeletedEvent.class, gender.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenGenderDoesNotExist() {
        FakeGenderRepository repository = new FakeGenderRepository();
        DeleteGenderUseCase useCase = new DeleteGenderUseCase(repository);

        assertThrows(GenderNotFoundApplicationException.class,
                () -> useCase.execute(GenderId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}