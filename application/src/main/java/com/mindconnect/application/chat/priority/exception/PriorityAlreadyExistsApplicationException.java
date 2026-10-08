package com.mindconnect.application.chat.priority.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class PriorityAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public PriorityAlreadyExistsApplicationException(String name) {
        super("Priority already exists with name: " + name);
    }
}