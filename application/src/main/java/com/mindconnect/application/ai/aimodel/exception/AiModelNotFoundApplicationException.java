package com.mindconnect.application.ai.aimodel.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class AiModelNotFoundApplicationException extends NotFoundApplicationException {

    public AiModelNotFoundApplicationException(String id) {
        super("AiModel not found with id: " + id);
    }
}