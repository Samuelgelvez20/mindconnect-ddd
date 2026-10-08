package com.mindconnect.application.ai.aiprovider.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class AiProviderAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public AiProviderAlreadyExistsApplicationException(String name) {
        super("AiProvider already exists with name: " + name);
    }
}