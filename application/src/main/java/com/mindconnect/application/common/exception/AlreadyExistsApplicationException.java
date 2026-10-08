package com.mindconnect.application.common.exception;

/** Base for uniqueness violations; the REST layer maps it to HTTP 409. */
public abstract class AlreadyExistsApplicationException extends ApplicationException {

    protected AlreadyExistsApplicationException(String message) {
        super(message);
    }
}
