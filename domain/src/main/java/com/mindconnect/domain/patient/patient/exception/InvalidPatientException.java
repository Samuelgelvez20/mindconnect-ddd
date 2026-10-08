package com.mindconnect.domain.patient.patient.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidPatientException extends DomainException {

    public InvalidPatientException(String message) {
        super(message);
    }
}