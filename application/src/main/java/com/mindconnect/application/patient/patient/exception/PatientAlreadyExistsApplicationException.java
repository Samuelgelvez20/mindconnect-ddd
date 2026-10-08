package com.mindconnect.application.patient.patient.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class PatientAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public PatientAlreadyExistsApplicationException(String email) {
        super("Patient already exists with email: " + email);
    }

    public PatientAlreadyExistsApplicationException(
            DocumentTypeId documentTypeId,
            String documentNumber) {
        super("Patient already exists with document type: " + documentTypeId.value()
                + " and document number: " + documentNumber);
    }
}