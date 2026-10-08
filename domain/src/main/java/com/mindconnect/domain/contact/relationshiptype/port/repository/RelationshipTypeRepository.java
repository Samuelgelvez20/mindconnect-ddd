package com.mindconnect.domain.contact.relationshiptype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public interface RelationshipTypeRepository {

    RelationshipType save(RelationshipType relationshipType);

    Optional<RelationshipType> findById(RelationshipTypeId id);

    List<RelationshipType> findAll();

    boolean existsByDescription(String description);

    boolean existsByDescriptionAndIdNot(String description, RelationshipTypeId id);

    void delete(RelationshipType relationshipType);
}