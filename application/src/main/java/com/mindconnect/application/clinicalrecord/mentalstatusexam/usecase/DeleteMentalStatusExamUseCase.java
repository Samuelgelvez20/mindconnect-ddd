package com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase;

import com.mindconnect.application.clinicalrecord.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class DeleteMentalStatusExamUseCase {

    private final MentalStatusExamRepository repository;

    public DeleteMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        this.repository = repository;
    }

    public void execute(MentalStatusExamId id) {

        var exam = repository.findById(id)
                .orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(id));

        exam.delete();
        repository.delete(exam);
    }
}