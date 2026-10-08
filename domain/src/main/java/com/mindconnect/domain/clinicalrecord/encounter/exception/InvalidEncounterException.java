package com.mindconnect.domain.clinicalrecord.encounter.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidEncounterException extends DomainException {

    public InvalidEncounterException(String message) {
        super(message);
    }
}