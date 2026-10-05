package com.mindconnect.domain.treatment.treatmentstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidTreatmentStatusException extends DomainException {

    public InvalidTreatmentStatusException(String message) {
        super(message);
    }
}