package com.mindconnect.application.clinicalrecord.clinicalnote.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;

public class ClinicalNoteNotFoundApplicationException extends NotFoundApplicationException {

    public ClinicalNoteNotFoundApplicationException(ClinicalNoteId id) {
        super("ClinicalNote not found with id: " + id.value());
    }
}