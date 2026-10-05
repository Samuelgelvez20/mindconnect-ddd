package com.mindconnect.application.clinicalrecord.mentalstatusexam.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;

public class MentalStatusExamNotFoundApplicationException extends NotFoundApplicationException {

    public MentalStatusExamNotFoundApplicationException(MentalStatusExamId id) {
        super("MentalStatusExam not found with id: " + id.value());
    }
}