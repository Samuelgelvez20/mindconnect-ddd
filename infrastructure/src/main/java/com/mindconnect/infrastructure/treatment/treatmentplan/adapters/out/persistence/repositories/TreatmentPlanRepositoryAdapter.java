package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;

import java.util.List;
import java.util.Optional;

public class TreatmentPlanRepositoryAdapter implements TreatmentPlanRepository {

    private final TreatmentPlanJpaRepository jpaRepository;

    public TreatmentPlanRepositoryAdapter(TreatmentPlanJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TreatmentPlan save(TreatmentPlan treatmentPlan) {
        return jpaRepository.save(treatmentPlan);
    }

    @Override
    public Optional<TreatmentPlan> findById(TreatmentPlanId id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TreatmentPlan> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(TreatmentPlan treatmentPlan) {
        jpaRepository.delete(treatmentPlan);
    }
}