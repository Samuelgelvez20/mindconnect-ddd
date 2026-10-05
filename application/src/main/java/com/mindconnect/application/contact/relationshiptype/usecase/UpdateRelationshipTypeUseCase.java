package com.mindconnect.application.contact.relationshiptype.usecase;

import com.mindconnect.application.contact.relationshiptype.command.UpdateRelationshipTypeCommand;
import com.mindconnect.application.contact.relationshiptype.dto.RelationshipTypeResponse;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeAlreadyExistsApplicationException;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;

public class UpdateRelationshipTypeUseCase {

    private final RelationshipTypeRepository relationshipTypeRepository;

    public UpdateRelationshipTypeUseCase(RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    public RelationshipTypeResponse execute(UpdateRelationshipTypeCommand command) {

        var relationshipType = relationshipTypeRepository.findById(command.id())
                .orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(command.id()));

        relationshipType.update(command.description());

        if (relationshipTypeRepository.existsByDescriptionAndIdNot(relationshipType.description(), relationshipType.id())) {
            throw new RelationshipTypeAlreadyExistsApplicationException(relationshipType.description());
        }

        return RelationshipTypeResponse.from(relationshipTypeRepository.save(relationshipType));
    }
}