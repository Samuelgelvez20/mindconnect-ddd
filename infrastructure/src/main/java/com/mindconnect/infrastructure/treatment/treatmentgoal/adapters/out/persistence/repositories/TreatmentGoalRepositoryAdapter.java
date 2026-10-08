package com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentgoal.model.aggregate.TreatmentGoal;
import com.mindconnect.domain.treatment.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.entity.TreatmentGoalJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.mappers.TreatmentGoalPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TreatmentGoalRepositoryAdapter implements TreatmentGoalRepository {

    private final TreatmentGoalJpaRepository jpaRepository;
    private final TreatmentGoalPersistenceMapper mapper;

    public TreatmentGoalRepositoryAdapter(TreatmentGoalJpaRepository jpaRepository, TreatmentGoalPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TreatmentGoal save(TreatmentGoal treatmentGoal) {
        TreatmentGoalJpaEntity entity = mapper.toJpa(treatmentGoal);
        TreatmentGoalJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<TreatmentGoal> findById(TreatmentGoalId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TreatmentGoal> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(TreatmentGoal treatmentGoal) {
        jpaRepository.deleteById(treatmentGoal.id().value());
    }

    public List<TreatmentGoal> findByTreatmentPlanId(TreatmentPlanId treatmentPlanId) {
        return jpaRepository.findByTreatmentPlanId(treatmentPlanId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}