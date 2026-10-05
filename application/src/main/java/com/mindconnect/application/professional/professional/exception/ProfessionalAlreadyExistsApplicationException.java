package com.mindconnect.application.professional.professional.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public class ProfessionalAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ProfessionalAlreadyExistsApplicationException(String licenseNumber) {
        super("Professional already exists with license number: " + licenseNumber);
    }

    public ProfessionalAlreadyExistsApplicationException(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber) {
        super("Professional already exists with document type: " + documentTypeId.value()
                + " and document number: " + documentNumber);
    }
}