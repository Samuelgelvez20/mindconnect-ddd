package com.mindconnect.application.chat.messagetype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;

public class MessageTypeNotFoundApplicationException extends NotFoundApplicationException {

    public MessageTypeNotFoundApplicationException(MessageTypeId id) {
        super("MessageType not found with id: " + id.value());
    }
}