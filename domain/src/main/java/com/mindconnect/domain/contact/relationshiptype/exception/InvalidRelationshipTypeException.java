package com.mindconnect.domain.contact.relationshiptype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidRelationshipTypeException extends DomainException {

    public InvalidRelationshipTypeException(String message) {
        super(message);
    }
}