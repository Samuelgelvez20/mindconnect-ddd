package com.mindconnect.application.treatment.treatmentstatus.usecase;

import com.mindconnect.application.treatment.treatmentstatus.dto.TreatmentStatusResponse;
import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListTreatmentStatusUseCase {

    private final TreatmentStatusRepository repository;

    public ListTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentStatusResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentStatusResponse::from)
                .collect(Collectors.toList());
    }
}