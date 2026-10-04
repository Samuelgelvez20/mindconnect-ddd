package com.mindconnect.application.referencedata.gender.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class GenderAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public GenderAlreadyExistsApplicationException(String description) {
        super("Gender already exists with description: " + description);
    }
}