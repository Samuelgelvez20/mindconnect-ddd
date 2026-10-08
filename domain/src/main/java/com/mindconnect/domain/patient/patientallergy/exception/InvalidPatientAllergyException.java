package com.mindconnect.domain.patient.patientallergy.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidPatientAllergyException extends DomainException {

    public InvalidPatientAllergyException(String message) {
        super(message);
    }
}