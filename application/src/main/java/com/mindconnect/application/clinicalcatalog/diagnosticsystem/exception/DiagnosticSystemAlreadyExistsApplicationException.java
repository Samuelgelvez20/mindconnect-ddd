package com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class DiagnosticSystemAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public DiagnosticSystemAlreadyExistsApplicationException(String code) {
        super("DiagnosticSystem with code " + code + " already exists");
    }
}