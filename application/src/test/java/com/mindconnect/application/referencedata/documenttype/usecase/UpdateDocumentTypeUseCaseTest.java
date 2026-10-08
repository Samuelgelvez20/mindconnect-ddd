package com.mindconnect.application.referencedata.documenttype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.documenttype.command.UpdateDocumentTypeCommand;
import com.mindconnect.application.referencedata.documenttype.dto.DocumentTypeResponse;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

class UpdateDocumentTypeUseCaseTest {

    @Test
    void shouldUpdateExistingDocumentType() {
        DocumentType documentType = DocumentType.register("CC", "Cédula");
        FakeDocumentTypeRepository repository = new FakeDocumentTypeRepository().with(documentType);
        UpdateDocumentTypeUseCase useCase = new UpdateDocumentTypeUseCase(repository);

        DocumentTypeResponse response = useCase.execute(
                new UpdateDocumentTypeCommand(documentType.id(), "TI", "Tarjeta de Identidad", false));

        assertEquals("TI", response.code());
        assertEquals("Tarjeta de Identidad", response.name());
        assertEquals(false, response.active());
    }

    @Test
    void shouldAllowKeepingTheSameCode() {
        DocumentType documentType = DocumentType.register("CC", "Cédula");
        UpdateDocumentTypeUseCase useCase =
                new UpdateDocumentTypeUseCase(new FakeDocumentTypeRepository().with(documentType));

        DocumentTypeResponse response = useCase.execute(
                new UpdateDocumentTypeCommand(documentType.id(), "CC", "Cédula Actualizada", true));

        assertEquals("CC", response.code());
    }

    @Test
    void shouldRejectWhenDocumentTypeDoesNotExist() {
        UpdateDocumentTypeUseCase useCase = new UpdateDocumentTypeUseCase(new FakeDocumentTypeRepository());

        assertThrows(DocumentTypeNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateDocumentTypeCommand(DocumentTypeId.generate(), "TI", "Tarjeta", true)));
    }

    @Test
    void shouldRejectCodeUsedByAnotherDocumentType() {
        DocumentType cc = DocumentType.register("CC", "Cédula");
        DocumentType ti = DocumentType.register("TI", "Tarjeta");
        UpdateDocumentTypeUseCase useCase =
                new UpdateDocumentTypeUseCase(new FakeDocumentTypeRepository().with(cc, ti));

        assertThrows(DocumentTypeAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateDocumentTypeCommand(ti.id(), "CC", "Tarjeta", true)));
    }
}