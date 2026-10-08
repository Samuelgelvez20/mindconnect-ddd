package com.mindconnect.application.ai.chatairun.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class ChatAiRunNotFoundApplicationException extends NotFoundApplicationException {

    public ChatAiRunNotFoundApplicationException(String id) {
        super("ChatAiRun not found with id: " + id);
    }
}