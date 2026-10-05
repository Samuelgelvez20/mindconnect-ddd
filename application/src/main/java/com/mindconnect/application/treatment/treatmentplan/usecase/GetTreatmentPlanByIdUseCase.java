package com.mindconnect.application.treatment.treatmentplan.usecase;

import com.mindconnect.application.treatment.treatmentplan.dto.TreatmentPlanResponse;
import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.application.treatment.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;

public class GetTreatmentPlanByIdUseCase {

    private final TreatmentPlanRepository repository;

    public GetTreatmentPlanByIdUseCase(TreatmentPlanRepository repository) {
        this.repository = repository;
    }

    public TreatmentPlanResponse execute(TreatmentPlanId id) {
        TreatmentPlan plan = repository.findById(id)
                .orElseThrow(() -> new TreatmentPlanNotFoundApplicationException("TreatmentPlan with id " + id.value() + " not found"));

        return TreatmentPlanResponse.from(plan);
    }
}