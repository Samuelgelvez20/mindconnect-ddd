package com.mindconnect.application.common.exception;

/** Base for "resource not found" errors; the REST layer maps it to HTTP 404. */
public abstract class NotFoundApplicationException extends ApplicationException {

    protected NotFoundApplicationException(String message) {
        super(message);
    }
}
