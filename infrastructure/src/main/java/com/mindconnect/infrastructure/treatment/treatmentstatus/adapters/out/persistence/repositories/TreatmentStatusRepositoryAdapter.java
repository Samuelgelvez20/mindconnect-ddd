package com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.entity.TreatmentStatusJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.mappers.TreatmentStatusPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TreatmentStatusRepositoryAdapter implements TreatmentStatusRepository {

    private final TreatmentStatusJpaRepository jpaRepository;
    private final TreatmentStatusPersistenceMapper mapper;

    public TreatmentStatusRepositoryAdapter(TreatmentStatusJpaRepository jpaRepository, TreatmentStatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TreatmentStatus save(TreatmentStatus treatmentStatus) {
        TreatmentStatusJpaEntity entity = mapper.toJpa(treatmentStatus);
        TreatmentStatusJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<TreatmentStatus> findById(TreatmentStatusId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TreatmentStatus> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(TreatmentStatus treatmentStatus) {
        jpaRepository.deleteById(treatmentStatus.id().value());
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