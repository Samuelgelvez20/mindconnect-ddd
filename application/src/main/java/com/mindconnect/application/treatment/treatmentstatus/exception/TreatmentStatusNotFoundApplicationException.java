package com.mindconnect.application.treatment.treatmentstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class TreatmentStatusNotFoundApplicationException extends NotFoundApplicationException {

    public TreatmentStatusNotFoundApplicationException(String id) {
        super("TreatmentStatus with id " + id + " not found");
    }
}