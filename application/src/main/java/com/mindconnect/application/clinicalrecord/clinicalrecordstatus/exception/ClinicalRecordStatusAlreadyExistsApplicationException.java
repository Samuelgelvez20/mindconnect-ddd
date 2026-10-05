package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ClinicalRecordStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ClinicalRecordStatusAlreadyExistsApplicationException(String code) {
        super("ClinicalRecordStatus already exists with code: " + code);
    }

    public ClinicalRecordStatusAlreadyExistsApplicationException(String code, String name) {
        super("ClinicalRecordStatus already exists with code: " + code + " or name: " + name);
    }
}