package com.mindconnect.application.treatment.treatmentstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class TreatmentStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public TreatmentStatusAlreadyExistsApplicationException(String code) {
        super("TreatmentStatus with code " + code + " already exists");
    }
}