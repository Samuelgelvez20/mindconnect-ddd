package com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.entity.TreatmentGoalStatusJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.mappers.TreatmentGoalStatusPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TreatmentGoalStatusRepositoryAdapter implements TreatmentGoalStatusRepository {

    private final TreatmentGoalStatusJpaRepository jpaRepository;
    private final TreatmentGoalStatusPersistenceMapper mapper;

    public TreatmentGoalStatusRepositoryAdapter(TreatmentGoalStatusJpaRepository jpaRepository, TreatmentGoalStatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TreatmentGoalStatus save(TreatmentGoalStatus treatmentGoalStatus) {
        TreatmentGoalStatusJpaEntity entity = mapper.toJpa(treatmentGoalStatus);
        TreatmentGoalStatusJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TreatmentGoalStatus> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(TreatmentGoalStatus treatmentGoalStatus) {
        jpaRepository.deleteById(treatmentGoalStatus.id().value());
    }

    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    public boolean existsByCodeAndIdNot(String code, UUID id) {
        return jpaRepository.existsByCodeAndIdNot(code, id);
    }

    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, UUID id) {
        return jpaRepository.existsByNameAndIdNot(name, id);
    }
}