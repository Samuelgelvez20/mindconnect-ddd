package com.mindconnect.domain.clinicalrecord.risklevel.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidRiskLevelException extends DomainException {

    public InvalidRiskLevelException(String message) {
        super(message);
    }
}