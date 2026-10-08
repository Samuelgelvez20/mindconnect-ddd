package com.mindconnect.application.ai.aiprovider.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class AiProviderNotFoundApplicationException extends NotFoundApplicationException {

    public AiProviderNotFoundApplicationException(String id) {
        super("AiProvider not found with id: " + id);
    }
}