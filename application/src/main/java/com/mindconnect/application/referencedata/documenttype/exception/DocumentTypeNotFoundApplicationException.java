package com.mindconnect.application.referencedata.documenttype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class DocumentTypeNotFoundApplicationException extends NotFoundApplicationException {

    public DocumentTypeNotFoundApplicationException(DocumentTypeId id) {
        super("DocumentType not found with id: " + id.value());
    }
}