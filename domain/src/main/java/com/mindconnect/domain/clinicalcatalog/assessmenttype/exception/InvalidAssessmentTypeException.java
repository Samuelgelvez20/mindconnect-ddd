package com.mindconnect.domain.clinicalcatalog.assessmenttype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidAssessmentTypeException extends DomainException {

    public InvalidAssessmentTypeException(String message) {
        super(message);
    }
}