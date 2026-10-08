package com.mindconnect.domain.clinicalcatalog.medicationroute.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidMedicationRouteException extends DomainException {

    public InvalidMedicationRouteException(String message) {
        super(message);
    }
}