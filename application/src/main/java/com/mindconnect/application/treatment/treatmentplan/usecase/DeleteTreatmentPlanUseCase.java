package com.mindconnect.application.treatment.treatmentplan.usecase;

import com.mindconnect.application.treatment.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;

public class DeleteTreatmentPlanUseCase {

    private final TreatmentPlanRepository repository;

    public DeleteTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        this.repository = repository;
    }

    public void execute(TreatmentPlanId id) {
        TreatmentPlan plan = repository.findById(id)
                .orElseThrow(() -> new TreatmentPlanNotFoundApplicationException("TreatmentPlan with id " + id.value() + " not found"));

        repository.delete(plan);
    }
}