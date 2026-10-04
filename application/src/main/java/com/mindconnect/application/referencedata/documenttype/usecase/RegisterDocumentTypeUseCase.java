package com.mindconnect.application.referencedata.documenttype.usecase;

import com.mindconnect.application.referencedata.documenttype.command.RegisterDocumentTypeCommand;
import com.mindconnect.application.referencedata.documenttype.dto.DocumentTypeResponse;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;

public class RegisterDocumentTypeUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    public RegisterDocumentTypeUseCase(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public DocumentTypeResponse execute(RegisterDocumentTypeCommand command) {

        DocumentType documentType = DocumentType.register(command.code(), command.name());

        if (documentTypeRepository.existsByCode(documentType.code())) {
            throw new DocumentTypeAlreadyExistsApplicationException(documentType.code());
        }

        return DocumentTypeResponse.from(documentTypeRepository.save(documentType));
    }
}