package com.mindconnect.application.contact.emailcontact.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.contact.emailcontact.model.valueobject.EmailContactId;

public class EmailContactNotFoundApplicationException extends NotFoundApplicationException {

    public EmailContactNotFoundApplicationException(EmailContactId id) {
        super("EmailContact not found with id: " + id.value());
    }
}