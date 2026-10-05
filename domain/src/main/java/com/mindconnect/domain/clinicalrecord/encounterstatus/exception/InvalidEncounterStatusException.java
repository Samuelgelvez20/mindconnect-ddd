package com.mindconnect.domain.clinicalrecord.encounterstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidEncounterStatusException extends DomainException {

    public InvalidEncounterStatusException(String message) {
        super(message);
    }
}