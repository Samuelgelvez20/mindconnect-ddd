package com.mindconnect.application.chat.priority.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public class PriorityNotFoundApplicationException extends NotFoundApplicationException {

    public PriorityNotFoundApplicationException(PriorityId id) {
        super("Priority not found with id: " + id.value());
    }
}