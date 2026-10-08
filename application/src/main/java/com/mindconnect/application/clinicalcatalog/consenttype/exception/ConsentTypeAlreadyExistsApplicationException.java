package com.mindconnect.application.clinicalcatalog.consenttype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ConsentTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ConsentTypeAlreadyExistsApplicationException(String code) {
        super("ConsentType with code " + code + " already exists");
    }
}