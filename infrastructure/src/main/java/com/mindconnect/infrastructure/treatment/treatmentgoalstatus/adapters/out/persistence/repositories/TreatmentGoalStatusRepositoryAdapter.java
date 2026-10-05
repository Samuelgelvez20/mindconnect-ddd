package com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;

import java.util.List;
import java.util.Optional;

public class TreatmentGoalStatusRepositoryAdapter implements TreatmentGoalStatusRepository {

    private final TreatmentGoalStatusJpaRepository jpaRepository;

    public TreatmentGoalStatusRepositoryAdapter(TreatmentGoalStatusJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TreatmentGoalStatus save(TreatmentGoalStatus treatmentGoalStatus) {
        return jpaRepository.save(treatmentGoalStatus);
    }

    @Override
    public Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TreatmentGoalStatus> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(TreatmentGoalStatus treatmentGoalStatus) {
        jpaRepository.delete(treatmentGoalStatus);
    }
}