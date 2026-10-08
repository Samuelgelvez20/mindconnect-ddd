package com.mindconnect.application.treatment.treatmentgoal.usecase;

import com.mindconnect.application.treatment.treatmentgoal.dto.TreatmentGoalResponse;
import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListTreatmentGoalUseCase {

    private final TreatmentGoalRepository repository;

    public ListTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentGoalResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentGoalResponse::from)
                .collect(Collectors.toList());
    }
}