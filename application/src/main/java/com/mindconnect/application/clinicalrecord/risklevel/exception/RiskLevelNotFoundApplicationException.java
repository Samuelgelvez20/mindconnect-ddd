package com.mindconnect.application.clinicalrecord.risklevel.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

public class RiskLevelNotFoundApplicationException extends NotFoundApplicationException {

    public RiskLevelNotFoundApplicationException(RiskLevelId id) {
        super("RiskLevel not found with id: " + id.value());
    }
}