package com.mindconnect.application.referencedata.documenttype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class DocumentTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public DocumentTypeAlreadyExistsApplicationException(String code) {
        super("DocumentType already exists with code: " + code);
    }
}