package com.mindconnect.application.professional.professionalstudy.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;

public class ProfessionalStudyNotFoundApplicationException extends NotFoundApplicationException {

    public ProfessionalStudyNotFoundApplicationException(ProfessionalStudyId id) {
        super("ProfessionalStudy not found with id: " + id.value());
    }
}