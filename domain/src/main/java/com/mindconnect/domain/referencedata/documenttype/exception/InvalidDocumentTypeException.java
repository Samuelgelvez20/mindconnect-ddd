package com.mindconnect.domain.referencedata.documenttype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidDocumentTypeException extends DomainException {

    public InvalidDocumentTypeException(String message) {
        super(message);
    }
}