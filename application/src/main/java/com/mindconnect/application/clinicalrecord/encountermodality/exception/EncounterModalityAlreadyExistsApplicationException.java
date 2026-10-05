package com.mindconnect.application.clinicalrecord.encountermodality.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class EncounterModalityAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public EncounterModalityAlreadyExistsApplicationException(String code) {
        super("EncounterModality already exists with code: " + code);
    }
}