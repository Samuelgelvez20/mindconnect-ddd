package com.mindconnect.application.clinicalrecord.encountertype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;

public class EncounterTypeNotFoundApplicationException extends NotFoundApplicationException {

    public EncounterTypeNotFoundApplicationException(EncounterTypeId id) {
        super("EncounterType not found with id: " + id.value());
    }
}