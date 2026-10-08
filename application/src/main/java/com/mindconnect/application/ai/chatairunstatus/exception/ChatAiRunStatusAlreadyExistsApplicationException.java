package com.mindconnect.application.ai.chatairunstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ChatAiRunStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ChatAiRunStatusAlreadyExistsApplicationException(String name) {
        super("ChatAiRunStatus already exists with name: " + name);
    }
}