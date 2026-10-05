package com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;

import java.util.List;
import java.util.Optional;

public class TreatmentStatusRepositoryAdapter implements TreatmentStatusRepository {

    private final TreatmentStatusJpaRepository jpaRepository;

    public TreatmentStatusRepositoryAdapter(TreatmentStatusJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TreatmentStatus save(TreatmentStatus treatmentStatus) {
        return jpaRepository.save(treatmentStatus);
    }

    @Override
    public Optional<TreatmentStatus> findById(TreatmentStatusId id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TreatmentStatus> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(TreatmentStatus treatmentStatus) {
        jpaRepository.delete(treatmentStatus);
    }
}