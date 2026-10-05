package com.mindconnect.application.clinicalrecord.clinicalrecord.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;

public class ClinicalRecordNotFoundApplicationException extends NotFoundApplicationException {

    public ClinicalRecordNotFoundApplicationException(ClinicalRecordId id) {
        super("ClinicalRecord not found with id: " + id.value());
    }
}