package com.mindconnect.application.treatment.treatmentgoalstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class TreatmentGoalStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public TreatmentGoalStatusAlreadyExistsApplicationException(String message) {
        super(message);
    }
}