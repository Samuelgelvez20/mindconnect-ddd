package com.mindconnect.application.chat.messagetype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class MessageTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public MessageTypeAlreadyExistsApplicationException(String name) {
        super("MessageType already exists with name: " + name);
    }
}