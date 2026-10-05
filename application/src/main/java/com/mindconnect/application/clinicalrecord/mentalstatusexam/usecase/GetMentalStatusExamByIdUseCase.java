package com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase;

import com.mindconnect.application.clinicalrecord.mentalstatusexam.dto.MentalStatusExamResponse;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class GetMentalStatusExamByIdUseCase {

    private final MentalStatusExamRepository repository;

    public GetMentalStatusExamByIdUseCase(MentalStatusExamRepository repository) {
        this.repository = repository;
    }

    public MentalStatusExamResponse execute(MentalStatusExamId id) {
        return repository.findById(id)
                .map(MentalStatusExamResponse::from)
                .orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(id));
    }
}