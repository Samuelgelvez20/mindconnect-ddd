package com.mindconnect.domain.professional.professionaltype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidProfessionalTypeException extends DomainException {

    public InvalidProfessionalTypeException(String message) {
        super(message);
    }
}