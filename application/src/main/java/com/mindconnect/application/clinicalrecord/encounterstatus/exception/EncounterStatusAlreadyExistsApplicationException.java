package com.mindconnect.application.clinicalrecord.encounterstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class EncounterStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public EncounterStatusAlreadyExistsApplicationException(String code) {
        super("EncounterStatus already exists with code: " + code);
    }

    public EncounterStatusAlreadyExistsApplicationException(String code, String name) {
        super("EncounterStatus already exists with code: " + code + " or name: " + name);
    }
}