package com.mindconnect.application.chat.chatescalationstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ChatEscalationStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ChatEscalationStatusAlreadyExistsApplicationException(String name) {
        super("ChatEscalationStatus already exists with name: " + name);
    }
}