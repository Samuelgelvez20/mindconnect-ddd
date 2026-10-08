package com.mindconnect.domain.clinicalrecord.encountermodality.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidEncounterModalityException extends DomainException {

    public InvalidEncounterModalityException(String message) {
        super(message);
    }
}