package com.mindconnect.application.clinicalcatalog.medicationroute.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class MedicationRouteNotFoundApplicationException extends NotFoundApplicationException {

    public MedicationRouteNotFoundApplicationException(String id) {
        super("MedicationRoute with id " + id + " not found");
    }
}