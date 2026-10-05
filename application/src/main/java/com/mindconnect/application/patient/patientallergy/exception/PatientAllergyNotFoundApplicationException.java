package com.mindconnect.application.patient.patientallergy.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;

public class PatientAllergyNotFoundApplicationException extends NotFoundApplicationException {

    public PatientAllergyNotFoundApplicationException(PatientAllergyId id) {
        super("PatientAllergy not found with id: " + id.value());
    }
}