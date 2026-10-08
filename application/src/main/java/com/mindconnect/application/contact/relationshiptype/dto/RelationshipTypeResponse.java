package com.mindconnect.application.contact.relationshiptype.dto;

import java.util.UUID;

import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;

public record RelationshipTypeResponse(
        UUID id,
        String description
) {

    public static RelationshipTypeResponse from(RelationshipType relationshipType) {
        return new RelationshipTypeResponse(
                relationshipType.id().value(),
                relationshipType.description()
        );
    }
}