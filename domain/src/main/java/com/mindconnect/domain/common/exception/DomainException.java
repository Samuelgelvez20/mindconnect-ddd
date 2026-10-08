package com.mindconnect.domain.common.exception;

/**
 * Base class for business-rule violations raised by aggregates.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
