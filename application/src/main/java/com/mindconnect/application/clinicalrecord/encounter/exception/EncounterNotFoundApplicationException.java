package com.mindconnect.application.clinicalrecord.encounter.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;

public class EncounterNotFoundApplicationException extends NotFoundApplicationException {

    public EncounterNotFoundApplicationException(EncounterId id) {
        super("Encounter not found with id: " + id.value());
    }
}