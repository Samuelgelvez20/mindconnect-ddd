package com.mindconnect.application.referencedata.documenttype.usecase;

import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;

public class DeleteDocumentTypeUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    public DeleteDocumentTypeUseCase(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public void execute(DocumentTypeId id) {

        var documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id));

        documentType.delete();
        documentTypeRepository.delete(documentType);
    }
}