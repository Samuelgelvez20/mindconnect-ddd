package com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidClinicalRecordStatusException extends DomainException {

    public InvalidClinicalRecordStatusException(String message) {
        super(message);
    }
}