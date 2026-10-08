package com.mindconnect.application.ai.chatairunerror.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class ChatAiRunErrorNotFoundApplicationException extends NotFoundApplicationException {

    public ChatAiRunErrorNotFoundApplicationException(String id) {
        super("ChatAiRunError not found with id: " + id);
    }
}