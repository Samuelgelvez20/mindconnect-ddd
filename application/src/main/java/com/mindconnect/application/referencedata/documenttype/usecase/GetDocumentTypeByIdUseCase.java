package com.mindconnect.application.referencedata.documenttype.usecase;

import com.mindconnect.application.referencedata.documenttype.dto.DocumentTypeResponse;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;

public class GetDocumentTypeByIdUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    public GetDocumentTypeByIdUseCase(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public DocumentTypeResponse execute(DocumentTypeId id) {
        return documentTypeRepository.findById(id)
                .map(DocumentTypeResponse::from)
                .orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id));
    }
}