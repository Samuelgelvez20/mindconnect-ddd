package com.mindconnect.application.clinicalrecord.riskassessment.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;

public class RiskAssessmentNotFoundApplicationException extends NotFoundApplicationException {

    public RiskAssessmentNotFoundApplicationException(RiskAssessmentId id) {
        super("RiskAssessment not found with id: " + id.value());
    }
}