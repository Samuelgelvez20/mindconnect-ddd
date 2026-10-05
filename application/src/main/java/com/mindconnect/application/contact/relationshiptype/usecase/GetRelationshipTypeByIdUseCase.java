package com.mindconnect.application.contact.relationshiptype.usecase;

import com.mindconnect.application.contact.relationshiptype.dto.RelationshipTypeResponse;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;

public class GetRelationshipTypeByIdUseCase {

    private final RelationshipTypeRepository relationshipTypeRepository;

    public GetRelationshipTypeByIdUseCase(RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    public RelationshipTypeResponse execute(RelationshipTypeId id) {
        return relationshipTypeRepository.findById(id)
                .map(RelationshipTypeResponse::from)
                .orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(id));
    }
}