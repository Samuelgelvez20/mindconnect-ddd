package com.mindconnect.application.contact.relationshiptype.command;

import java.util.Objects;

import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public record UpdateRelationshipTypeCommand(
        RelationshipTypeId id,
        String description) {

    public UpdateRelationshipTypeCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(description, "description must not be null");
    }
}