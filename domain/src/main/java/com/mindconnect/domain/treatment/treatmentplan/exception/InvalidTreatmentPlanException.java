package com.mindconnect.domain.treatment.treatmentplan.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidTreatmentPlanException extends DomainException {

    public InvalidTreatmentPlanException(String message) {
        super(message);
    }
}