package com.mindconnect.domain.treatment.treatmentplan.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;

public interface TreatmentPlanRepository {

    TreatmentPlan save(TreatmentPlan treatmentPlan);

    Optional<TreatmentPlan> findById(TreatmentPlanId id);

    List<TreatmentPlan> findAll();

    void delete(TreatmentPlan treatmentPlan);
}