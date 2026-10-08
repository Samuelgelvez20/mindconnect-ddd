package com.mindconnect.domain.clinicalrecord.riskassessment.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidRiskAssessmentException extends DomainException {

    public InvalidRiskAssessmentException(String message) {
        super(message);
    }
}