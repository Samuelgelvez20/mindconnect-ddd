package com.mindconnect.domain.treatment.treatmentgoal.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidTreatmentGoalException extends DomainException {

    public InvalidTreatmentGoalException(String message) {
        super(message);
    }
}