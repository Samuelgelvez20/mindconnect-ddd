package com.mindconnect.application.professional.professionaltype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professionaltype.command.RegisterProfessionalTypeCommand;
import com.mindconnect.application.professional.professionaltype.dto.ProfessionalTypeResponse;
import com.mindconnect.application.professional.professionaltype.exception.ProfessionalTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.professional.professionaltype.exception.InvalidProfessionalTypeException;
import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;

class RegisterProfessionalTypeUseCaseTest {

    @Test
    void shouldRegisterAndPersistProfessionalType() {
        FakeProfessionalTypeRepository repository = new FakeProfessionalTypeRepository();
        RegisterProfessionalTypeUseCase useCase = new RegisterProfessionalTypeUseCase(repository);

        ProfessionalTypeResponse response = useCase.execute(
                new RegisterProfessionalTypeCommand("Psychologist"));

        assertNotNull(response.id());
        assertEquals("Psychologist", response.name());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedName() {
        FakeProfessionalTypeRepository repository =
                new FakeProfessionalTypeRepository().with(ProfessionalType.register("Psychologist"));
        RegisterProfessionalTypeUseCase useCase = new RegisterProfessionalTypeUseCase(repository);

        assertThrows(ProfessionalTypeAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterProfessionalTypeCommand("  Psychologist  ")));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterProfessionalTypeUseCase useCase = new RegisterProfessionalTypeUseCase(new FakeProfessionalTypeRepository());

        assertThrows(InvalidProfessionalTypeException.class,
                () -> useCase.execute(new RegisterProfessionalTypeCommand("  ")));
    }
}