package com.mindconnect.domain.clinicalrecord.clinicalnote.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidClinicalNoteException extends DomainException {

    public InvalidClinicalNoteException(String message) {
        super(message);
    }
}