package com.mindconnect.application.treatment.treatmentgoalstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class TreatmentGoalStatusNotFoundApplicationException extends NotFoundApplicationException {

    public TreatmentGoalStatusNotFoundApplicationException(String message) {
        super(message);
    }
}