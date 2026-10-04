package com.mindconnect.application.referencedata.stateregion.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public class StateRegionNotFoundApplicationException extends NotFoundApplicationException {

    public StateRegionNotFoundApplicationException(StateRegionId id) {
        super("StateRegion not found with id: " + id.value());
    }
}