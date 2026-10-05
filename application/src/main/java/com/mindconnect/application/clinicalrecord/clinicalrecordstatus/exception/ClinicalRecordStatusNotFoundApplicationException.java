package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;

public class ClinicalRecordStatusNotFoundApplicationException extends NotFoundApplicationException {

    public ClinicalRecordStatusNotFoundApplicationException(ClinicalRecordStatusId id) {
        super("ClinicalRecordStatus not found with id: " + id.value());
    }
}