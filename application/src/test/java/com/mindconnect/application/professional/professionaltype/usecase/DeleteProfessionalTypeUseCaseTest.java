package com.mindconnect.application.professional.professionaltype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.mindconnect.domain.professional.professionaltype.event.ProfessionalTypeDeletedEvent;
import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

class DeleteProfessionalTypeUseCaseTest {

    @Test
    void shouldDeleteExistingProfessionalTypeAndRecordEvent() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");
        FakeProfessionalTypeRepository repository = new FakeProfessionalTypeRepository().with(professionalType);
        DeleteProfessionalTypeUseCase useCase = new DeleteProfessionalTypeUseCase(repository);

        useCase.execute(professionalType.id());

        assertEquals(1, repository.deleted().size());
        assertSame(professionalType, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(ProfessionalTypeDeletedEvent.class, professionalType.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenProfessionalTypeDoesNotExist() {
        FakeProfessionalTypeRepository repository = new FakeProfessionalTypeRepository();
        DeleteProfessionalTypeUseCase useCase = new DeleteProfessionalTypeUseCase(repository);

        assertThrows(ProfessionalTypeNotFoundApplicationException.class,
                () -> useCase.execute(ProfessionalTypeId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}