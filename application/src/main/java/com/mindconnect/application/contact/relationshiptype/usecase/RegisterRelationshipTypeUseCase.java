package com.mindconnect.application.contact.relationshiptype.usecase;

import com.mindconnect.application.contact.relationshiptype.command.RegisterRelationshipTypeCommand;
import com.mindconnect.application.contact.relationshiptype.dto.RelationshipTypeResponse;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;

public class RegisterRelationshipTypeUseCase {

    private final RelationshipTypeRepository relationshipTypeRepository;

    public RegisterRelationshipTypeUseCase(RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    public RelationshipTypeResponse execute(RegisterRelationshipTypeCommand command) {

        RelationshipType relationshipType = RelationshipType.register(command.description());

        if (relationshipTypeRepository.existsByDescription(relationshipType.description())) {
            throw new RelationshipTypeAlreadyExistsApplicationException(relationshipType.description());
        }

        return RelationshipTypeResponse.from(relationshipTypeRepository.save(relationshipType));
    }
}