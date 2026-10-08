package com.mindconnect.application.ai.aimodel.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class AiModelAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public AiModelAlreadyExistsApplicationException(String modelKey) {
        super("AiModel already exists with modelKey: " + modelKey);
    }
}