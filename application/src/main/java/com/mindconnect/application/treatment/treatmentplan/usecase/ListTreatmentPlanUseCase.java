package com.mindconnect.application.treatment.treatmentplan.usecase;

import com.mindconnect.application.treatment.treatmentplan.dto.TreatmentPlanResponse;
import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListTreatmentPlanUseCase {

    private final TreatmentPlanRepository repository;

    public ListTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentPlanResponse> execute() {
        return repository.findAll().stream()
                .map(TreatmentPlanResponse::from)
                .collect(Collectors.toList());
    }
}