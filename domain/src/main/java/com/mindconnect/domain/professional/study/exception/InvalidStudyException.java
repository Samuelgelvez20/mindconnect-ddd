package com.mindconnect.domain.professional.study.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidStudyException extends DomainException {

    public InvalidStudyException(String message) {
        super(message);
    }
}