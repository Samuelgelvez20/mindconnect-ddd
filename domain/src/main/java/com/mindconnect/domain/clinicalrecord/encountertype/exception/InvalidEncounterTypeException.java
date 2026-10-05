package com.mindconnect.domain.clinicalrecord.encountertype.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidEncounterTypeException extends DomainException {

    public InvalidEncounterTypeException(String message) {
        super(message);
    }
}