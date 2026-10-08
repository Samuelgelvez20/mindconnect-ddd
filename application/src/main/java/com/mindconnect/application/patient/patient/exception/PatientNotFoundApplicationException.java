package com.mindconnect.application.patient.patient.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;

public class PatientNotFoundApplicationException extends NotFoundApplicationException {

    public PatientNotFoundApplicationException(PatientId id) {
        super("Patient not found with id: " + id.value());
    }
}