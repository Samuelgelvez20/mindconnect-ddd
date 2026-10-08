package com.mindconnect.domain.referencedata.gender.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidGenderException extends DomainException {

    public InvalidGenderException(String message) {
        super(message);
    }
}