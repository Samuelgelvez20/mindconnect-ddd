package com.mindconnect.domain.clinicalcatalog.diagnosticsystem.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidDiagnosticSystemException extends DomainException {

    public InvalidDiagnosticSystemException(String message) {
        super(message);
    }
}