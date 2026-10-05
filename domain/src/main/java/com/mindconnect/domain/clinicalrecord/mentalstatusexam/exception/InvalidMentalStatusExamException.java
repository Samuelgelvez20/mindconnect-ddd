package com.mindconnect.domain.clinicalrecord.mentalstatusexam.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidMentalStatusExamException extends DomainException {

    public InvalidMentalStatusExamException(String message) {
        super(message);
    }
}