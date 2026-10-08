package com.mindconnect.application.clinicalcatalog.assessmenttype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class AssessmentTypeNotFoundApplicationException extends NotFoundApplicationException {

    public AssessmentTypeNotFoundApplicationException(String id) {
        super("AssessmentType with id " + id + " not found");
    }
}