package com.mindconnect.application.professional.study.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;

public class StudyNotFoundApplicationException extends NotFoundApplicationException {

    public StudyNotFoundApplicationException(StudyId id) {
        super("Study not found with id: " + id.value());
    }
}