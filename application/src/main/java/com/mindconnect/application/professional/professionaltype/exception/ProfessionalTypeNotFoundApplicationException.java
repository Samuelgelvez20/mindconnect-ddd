package com.mindconnect.application.professional.professionaltype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public class ProfessionalTypeNotFoundApplicationException extends NotFoundApplicationException {

    public ProfessionalTypeNotFoundApplicationException(ProfessionalTypeId id) {
        super("ProfessionalType not found with id: " + id.value());
    }
}