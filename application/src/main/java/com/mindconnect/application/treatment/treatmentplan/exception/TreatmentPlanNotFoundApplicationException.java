package com.mindconnect.application.treatment.treatmentplan.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class TreatmentPlanNotFoundApplicationException extends NotFoundApplicationException {

    public TreatmentPlanNotFoundApplicationException(String id) {
        super("TreatmentPlan with id " + id + " not found");
    }
}