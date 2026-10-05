package com.mindconnect.application.treatment.treatmentgoal.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class TreatmentGoalAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public TreatmentGoalAlreadyExistsApplicationException(String message) {
        super(message);
    }
}