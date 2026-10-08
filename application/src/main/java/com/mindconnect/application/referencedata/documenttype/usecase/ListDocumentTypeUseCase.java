package com.mindconnect.application.referencedata.documenttype.usecase;

import java.util.List;

import com.mindconnect.application.referencedata.documenttype.dto.DocumentTypeResponse;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;

public class ListDocumentTypeUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    public ListDocumentTypeUseCase(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public List<DocumentTypeResponse> execute() {
        return documentTypeRepository.findAll()
                .stream()
                .map(DocumentTypeResponse::from)
                .toList();
    }
}