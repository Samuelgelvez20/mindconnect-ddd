package com.mindconnect.domain.referencedata.documenttype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public interface DocumentTypeRepository {

    DocumentType save(DocumentType documentType);

    Optional<DocumentType> findById(DocumentTypeId id);

    List<DocumentType> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, DocumentTypeId id);

    void delete(DocumentType documentType);
}