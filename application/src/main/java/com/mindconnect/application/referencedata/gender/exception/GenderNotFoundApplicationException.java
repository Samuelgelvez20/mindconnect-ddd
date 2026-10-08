package com.mindconnect.application.referencedata.gender.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public class GenderNotFoundApplicationException extends NotFoundApplicationException {

    public GenderNotFoundApplicationException(GenderId id) {
        super("Gender not found with id: " + id.value());
    }
}