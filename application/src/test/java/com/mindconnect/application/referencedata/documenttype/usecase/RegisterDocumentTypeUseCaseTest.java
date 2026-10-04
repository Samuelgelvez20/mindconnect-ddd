package com.mindconnect.application.referencedata.documenttype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.documenttype.command.RegisterDocumentTypeCommand;
import com.mindconnect.application.referencedata.documenttype.dto.DocumentTypeResponse;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.documenttype.exception.InvalidDocumentTypeException;
import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;

class RegisterDocumentTypeUseCaseTest {

    @Test
    void shouldRegisterAndPersistDocumentType() {
        FakeDocumentTypeRepository repository = new FakeDocumentTypeRepository();
        RegisterDocumentTypeUseCase useCase = new RegisterDocumentTypeUseCase(repository);

        DocumentTypeResponse response = useCase.execute(
                new RegisterDocumentTypeCommand("CC", "Cédula de Ciudadanía"));

        assertNotNull(response.id());
        assertEquals("CC", response.code());
        assertEquals("Cédula de Ciudadanía", response.name());
        assertTrue(response.active());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedCode() {
        FakeDocumentTypeRepository repository =
                new FakeDocumentTypeRepository().with(DocumentType.register("CC", "Cédula"));
        RegisterDocumentTypeUseCase useCase = new RegisterDocumentTypeUseCase(repository);

        assertThrows(DocumentTypeAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterDocumentTypeCommand("  CC  ", "Otro")));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterDocumentTypeUseCase useCase = new RegisterDocumentTypeUseCase(new FakeDocumentTypeRepository());

        assertThrows(InvalidDocumentTypeException.class,
                () -> useCase.execute(new RegisterDocumentTypeCommand("  ", "Nombre")));
    }
}