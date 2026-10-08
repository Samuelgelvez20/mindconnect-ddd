package com.mindconnect.application.contact.contact.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.contact.contact.model.valueobject.ContactId;

public class ContactNotFoundApplicationException extends NotFoundApplicationException {

    public ContactNotFoundApplicationException(ContactId id) {
        super("Contact not found with id: " + id.value());
    }
}