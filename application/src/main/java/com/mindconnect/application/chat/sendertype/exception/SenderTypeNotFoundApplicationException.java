package com.mindconnect.application.chat.sendertype.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;

public class SenderTypeNotFoundApplicationException extends NotFoundApplicationException {

    public SenderTypeNotFoundApplicationException(SenderTypeId id) {
        super("SenderType not found with id: " + id.value());
    }
}