package com.mindconnect.application.clinicalcatalog.consenttype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class ConsentTypeNotFoundApplicationException extends NotFoundApplicationException {

    public ConsentTypeNotFoundApplicationException(String id) {
        super("ConsentType with id " + id + " not found");
    }
}