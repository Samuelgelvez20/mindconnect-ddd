package com.mindconnect.application.treatment.treatmentplan.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class TreatmentPlanAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public TreatmentPlanAlreadyExistsApplicationException(String title) {
        super("TreatmentPlan with title " + title + " already exists");
    }
}