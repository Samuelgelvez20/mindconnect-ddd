package com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.mentalstatusexam.dto.MentalStatusExamResponse;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class ListMentalStatusExamUseCase {

    private final MentalStatusExamRepository repository;

    public ListMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        this.repository = repository;
    }

    public List<MentalStatusExamResponse> execute() {
        return repository.findAll()
                .stream()
                .map(MentalStatusExamResponse::from)
                .toList();
    }
}