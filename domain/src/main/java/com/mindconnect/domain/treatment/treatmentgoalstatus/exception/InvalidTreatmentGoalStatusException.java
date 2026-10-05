package com.mindconnect.domain.treatment.treatmentgoalstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidTreatmentGoalStatusException extends DomainException {

    public InvalidTreatmentGoalStatusException(String message) {
        super(message);
    }
}