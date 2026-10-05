package com.mindconnect.domain.clinicalrecord.clinicalrecord.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidClinicalRecordException extends DomainException {

    public InvalidClinicalRecordException(String message) {
        super(message);
    }
}