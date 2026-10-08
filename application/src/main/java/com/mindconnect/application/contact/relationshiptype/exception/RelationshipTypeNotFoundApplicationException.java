package com.mindconnect.application.contact.relationshiptype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

public class RelationshipTypeNotFoundApplicationException extends NotFoundApplicationException {

    public RelationshipTypeNotFoundApplicationException(RelationshipTypeId id) {
        super("RelationshipType not found with id: " + id.value());
    }
}