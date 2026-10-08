package com.mindconnect.application.clinicalcatalog.assessmenttype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class AssessmentTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public AssessmentTypeAlreadyExistsApplicationException(String code) {
        super("AssessmentType with code " + code + " already exists");
    }
}