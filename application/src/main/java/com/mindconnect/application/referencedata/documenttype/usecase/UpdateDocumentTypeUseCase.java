package com.mindconnect.application.referencedata.documenttype.usecase;

import com.mindconnect.application.referencedata.documenttype.command.UpdateDocumentTypeCommand;
import com.mindconnect.application.referencedata.documenttype.dto.DocumentTypeResponse;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;

public class UpdateDocumentTypeUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    public UpdateDocumentTypeUseCase(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public DocumentTypeResponse execute(UpdateDocumentTypeCommand command) {

        var documentType = documentTypeRepository.findById(command.id())
                .orElseThrow(() -> new DocumentTypeNotFoundApplicationException(command.id()));

        documentType.update(command.code(), command.name(), command.active());

        if (documentTypeRepository.existsByCodeAndIdNot(documentType.code(), documentType.id())) {
            throw new DocumentTypeAlreadyExistsApplicationException(documentType.code());
        }

        return DocumentTypeResponse.from(documentTypeRepository.save(documentType));
    }
}