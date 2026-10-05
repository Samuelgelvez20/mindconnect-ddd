package com.mindconnect.application.professional.professional.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class ProfessionalNotFoundApplicationException extends NotFoundApplicationException {

    public ProfessionalNotFoundApplicationException(ProfessionalId id) {
        super("Professional not found with id: " + id.value());
    }
}