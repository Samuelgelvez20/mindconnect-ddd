package com.mindconnect.domain.contact.patientcontact.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidPatientContactException extends DomainException {

    public InvalidPatientContactException(String message) {
        super(message);
    }
}