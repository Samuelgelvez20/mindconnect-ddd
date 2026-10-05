package com.mindconnect.application.contact.patientcontact.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;

public class PatientContactNotFoundApplicationException extends NotFoundApplicationException {

    public PatientContactNotFoundApplicationException(PatientContactId id) {
        super("PatientContact not found with id: " + id.value());
    }
}