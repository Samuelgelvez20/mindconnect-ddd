package com.mindconnect.application.chat.sendertype.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class SenderTypeAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public SenderTypeAlreadyExistsApplicationException(String name) {
        super("SenderType already exists with name: " + name);
    }
}