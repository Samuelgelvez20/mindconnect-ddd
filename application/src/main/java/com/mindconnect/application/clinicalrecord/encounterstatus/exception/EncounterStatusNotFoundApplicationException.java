package com.mindconnect.application.clinicalrecord.encounterstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

public class EncounterStatusNotFoundApplicationException extends NotFoundApplicationException {

    public EncounterStatusNotFoundApplicationException(EncounterStatusId id) {
        super("EncounterStatus not found with id: " + id.value());
    }
}