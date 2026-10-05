package com.mindconnect.application.treatment.treatmentgoal.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class TreatmentGoalNotFoundApplicationException extends NotFoundApplicationException {

    public TreatmentGoalNotFoundApplicationException(String message) {
        super(message);
    }
}