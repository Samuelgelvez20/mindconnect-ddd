package com.mindconnect.application.clinicalrecord.encountertype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class EncounterTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public EncounterTypeAlreadyExistsApplicationException(String code) {
        super("EncounterType already exists with code: " + code);
    }
}