package com.mindconnect.application.referencedata.documenttype.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.referencedata.documenttype.model.aggregate.DocumentType;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.documenttype.port.repository.DocumentTypeRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeDocumentTypeRepository implements DocumentTypeRepository {

    private final Map<DocumentTypeId, DocumentType> store = new LinkedHashMap<>();
    private final List<DocumentType> deleted = new ArrayList<>();

    FakeDocumentTypeRepository with(DocumentType... documentTypes) {
        for (DocumentType documentType : documentTypes) {
            store.put(documentType.id(), documentType);
        }
        return this;
    }

    List<DocumentType> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        store.put(documentType.id(), documentType);
        return documentType;
    }

    @Override
    public Optional<DocumentType> findById(DocumentTypeId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<DocumentType> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByCode(String code) {
        return store.values().stream().anyMatch(d -> d.code().equals(code));
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, DocumentTypeId id) {
        return store.values().stream()
                .anyMatch(d -> d.code().equals(code) && !d.id().equals(id));
    }

    @Override
    public void delete(DocumentType documentType) {
        store.remove(documentType.id());
        deleted.add(documentType);
    }
}