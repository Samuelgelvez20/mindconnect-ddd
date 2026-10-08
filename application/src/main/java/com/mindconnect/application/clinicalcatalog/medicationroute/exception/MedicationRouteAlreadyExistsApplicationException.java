package com.mindconnect.application.clinicalcatalog.medicationroute.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class MedicationRouteAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public MedicationRouteAlreadyExistsApplicationException(String code) {
        super("MedicationRoute with code " + code + " already exists");
    }
}