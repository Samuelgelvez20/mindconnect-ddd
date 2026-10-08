package com.mindconnect.application.clinicalrecord.encountermodality.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;

public class EncounterModalityNotFoundApplicationException extends NotFoundApplicationException {

    public EncounterModalityNotFoundApplicationException(EncounterModalityId id) {
        super("EncounterModality not found with id: " + id.value());
    }
}