package com.mindconnect.application.treatment.treatmentgoalstatus.usecase;

import com.mindconnect.application.treatment.treatmentgoalstatus.dto.TreatmentGoalStatusResponse;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListTreatmentGoalStatusUseCase {

    private final TreatmentGoalStatusRepository repository;

    public ListTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentGoalStatusResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentGoalStatusResponse::from)
                .collect(Collectors.toList());
    }
}