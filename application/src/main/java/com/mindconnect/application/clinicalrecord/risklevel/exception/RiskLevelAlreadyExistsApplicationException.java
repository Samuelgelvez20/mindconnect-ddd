package com.mindconnect.application.clinicalrecord.risklevel.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class RiskLevelAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public RiskLevelAlreadyExistsApplicationException(String code) {
        super("RiskLevel already exists with code: " + code);
    }
}