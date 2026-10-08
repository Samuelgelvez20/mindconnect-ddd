package com.mindconnect.application.professional.professionaltype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ProfessionalTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ProfessionalTypeAlreadyExistsApplicationException(String name) {
        super("ProfessionalType already exists with name: " + name);
    }
}