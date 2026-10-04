package com.mindconnect.domain.referencedata.stateregion.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidStateRegionException extends DomainException {

    public InvalidStateRegionException(String message) {
        super(message);
    }
}