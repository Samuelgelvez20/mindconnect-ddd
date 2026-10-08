package com.mindconnect.application.contact.phonecontact.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.contact.phonecontact.model.valueobject.PhoneContactId;

public class PhoneContactNotFoundApplicationException extends NotFoundApplicationException {

    public PhoneContactNotFoundApplicationException(PhoneContactId id) {
        super("PhoneContact not found with id: " + id.value());
    }
}