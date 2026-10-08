package com.mindconnect.application.ai.chatairunstatus.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class ChatAiRunStatusNotFoundApplicationException extends NotFoundApplicationException {

    public ChatAiRunStatusNotFoundApplicationException(String id) {
        super("ChatAiRunStatus not found with id: " + id);
    }
}