package com.mindconnect.application.contact.relationshiptype.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeRelationshipTypeRepository implements RelationshipTypeRepository {

    private final Map<RelationshipTypeId, RelationshipType> store = new LinkedHashMap<>();
    private final List<RelationshipType> deleted = new ArrayList<>();

    FakeRelationshipTypeRepository with(RelationshipType... relationshipTypes) {
        for (RelationshipType relationshipType : relationshipTypes) {
            store.put(relationshipType.id(), relationshipType);
        }
        return this;
    }

    List<RelationshipType> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public RelationshipType save(RelationshipType relationshipType) {
        store.put(relationshipType.id(), relationshipType);
        return relationshipType;
    }

    @Override
    public Optional<RelationshipType> findById(RelationshipTypeId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<RelationshipType> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByDescription(String description) {
        return store.values().stream().anyMatch(r -> r.description().equals(description));
    }

    @Override
    public boolean existsByDescriptionAndIdNot(String description, RelationshipTypeId id) {
        return store.values().stream()
                .anyMatch(r -> r.description().equals(description) && !r.id().equals(id));
    }

    @Override
    public void delete(RelationshipType relationshipType) {
        store.remove(relationshipType.id());
        deleted.add(relationshipType);
    }
}