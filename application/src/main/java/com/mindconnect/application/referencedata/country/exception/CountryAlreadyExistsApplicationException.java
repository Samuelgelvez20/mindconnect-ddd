package com.mindconnect.application.referencedata.country.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class CountryAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public CountryAlreadyExistsApplicationException(String code) {
        super("Country already exists with code: " + code);
    }
}
