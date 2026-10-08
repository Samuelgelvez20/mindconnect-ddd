package com.mindconnect.application.contact.relationshiptype.usecase;

import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;

public class DeleteRelationshipTypeUseCase {

    private final RelationshipTypeRepository relationshipTypeRepository;

    public DeleteRelationshipTypeUseCase(RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    public void execute(RelationshipTypeId id) {

        var relationshipType = relationshipTypeRepository.findById(id)
                .orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(id));

        relationshipType.delete();
        relationshipTypeRepository.delete(relationshipType);
    }
}