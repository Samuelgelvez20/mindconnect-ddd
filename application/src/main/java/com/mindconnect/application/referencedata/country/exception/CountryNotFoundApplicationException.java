package com.mindconnect.application.referencedata.country.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public class CountryNotFoundApplicationException extends NotFoundApplicationException {

    public CountryNotFoundApplicationException(CountryId id) {
        super("Country not found with id: " + id.value());
    }
}
