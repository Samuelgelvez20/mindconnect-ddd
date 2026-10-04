package com.mindconnect.infrastructure.referencedata.documenttype.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;
import com.mindconnect.infrastructure.referencedata.documenttype.adapters.out.persistence.mappers.DocumentTypePersistenceMapper;

public class DocumentTypeRepositoryAdapter implements DocumentTypeRepository {

    private final DocumentTypeJpaRepository documentTypeJpaRepository;
    private final DocumentTypePersistenceMapper mapper;

    public DocumentTypeRepositoryAdapter(
            DocumentTypeJpaRepository documentTypeJpaRepository,
            DocumentTypePersistenceMapper mapper) {
        this.documentTypeJpaRepository = documentTypeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        return mapper.toDomain(documentTypeJpaRepository.save(mapper.toJpa(documentType)));
    }

    @Override
    public Optional<DocumentType> findById(DocumentTypeId id) {
        return documentTypeJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<DocumentType> findAll() {
        return documentTypeJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return documentTypeJpaRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, DocumentTypeId id) {
        return documentTypeJpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(DocumentType documentType) {
        documentTypeJpaRepository.deleteById(documentType.id().value());
    }
}