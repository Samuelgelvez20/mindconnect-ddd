package com.mindconnect.application.contact.relationshiptype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class RelationshipTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public RelationshipTypeAlreadyExistsApplicationException(String description) {
        super("RelationshipType already exists with description: " + description);
    }
}