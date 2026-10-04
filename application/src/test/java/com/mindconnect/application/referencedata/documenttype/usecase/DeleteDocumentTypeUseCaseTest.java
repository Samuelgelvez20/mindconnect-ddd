package com.mindconnect.application.referencedata.documenttype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.mindconnect.domain.referencedata.documenttype.event.DocumentTypeDeletedEvent;
import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

class DeleteDocumentTypeUseCaseTest {

    @Test
    void shouldDeleteExistingDocumentTypeAndRecordEvent() {
        DocumentType documentType = DocumentType.register("CC", "Cédula");
        FakeDocumentTypeRepository repository = new FakeDocumentTypeRepository().with(documentType);
        DeleteDocumentTypeUseCase useCase = new DeleteDocumentTypeUseCase(repository);

        useCase.execute(documentType.id());

        assertEquals(1, repository.deleted().size());
        assertSame(documentType, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(DocumentTypeDeletedEvent.class, documentType.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenDocumentTypeDoesNotExist() {
        FakeDocumentTypeRepository repository = new FakeDocumentTypeRepository();
        DeleteDocumentTypeUseCase useCase = new DeleteDocumentTypeUseCase(repository);

        assertThrows(DocumentTypeNotFoundApplicationException.class,
                () -> useCase.execute(DocumentTypeId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}