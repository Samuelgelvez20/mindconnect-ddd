package com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class DiagnosticSystemNotFoundApplicationException extends NotFoundApplicationException {

    public DiagnosticSystemNotFoundApplicationException(String id) {
        super("DiagnosticSystem with id " + id + " not found");
    }
}