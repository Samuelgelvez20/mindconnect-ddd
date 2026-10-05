package com.mindconnect.application.professional.professionaltype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professionaltype.command.UpdateProfessionalTypeCommand;
import com.mindconnect.application.professional.professionaltype.dto.ProfessionalTypeResponse;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeAlreadyExistsApplicationException;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

class UpdateProfessionalTypeUseCaseTest {

    @Test
    void shouldUpdateExistingProfessionalType() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");
        FakeProfessionalTypeRepository repository = new FakeProfessionalTypeRepository().with(professionalType);
        UpdateProfessionalTypeUseCase useCase = new UpdateProfessionalTypeUseCase(repository);

        ProfessionalTypeResponse response = useCase.execute(
                new UpdateProfessionalTypeCommand(professionalType.id(), "Psychiatrist"));

        assertEquals("Psychiatrist", response.name());
    }

    @Test
    void shouldAllowKeepingTheSameName() {
        ProfessionalType professionalType = ProfessionalType.register("Psychologist");
        UpdateProfessionalTypeUseCase useCase =
                new UpdateProfessionalTypeUseCase(new FakeProfessionalTypeRepository().with(professionalType));

        ProfessionalTypeResponse response = useCase.execute(
                new UpdateProfessionalTypeCommand(professionalType.id(), "Psychologist"));

        assertEquals("Psychologist", response.name());
    }

    @Test
    void shouldRejectWhenProfessionalTypeDoesNotExist() {
        UpdateProfessionalTypeUseCase useCase = new UpdateProfessionalTypeUseCase(new FakeProfessionalTypeRepository());

        assertThrows(ProfessionalTypeNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateProfessionalTypeCommand(ProfessionalTypeId.generate(), "Psychiatrist")));
    }

    @Test
    void shouldRejectNameUsedByAnotherProfessionalType() {
        ProfessionalType psychologist = ProfessionalType.register("Psychologist");
        ProfessionalType psychiatrist = ProfessionalType.register("Psychiatrist");
        UpdateProfessionalTypeUseCase useCase =
                new UpdateProfessionalTypeUseCase(new FakeProfessionalTypeRepository().with(psychologist, psychiatrist));

        assertThrows(ProfessionalTypeAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateProfessionalTypeCommand(psychiatrist.id(), "Psychologist")));
    }
}