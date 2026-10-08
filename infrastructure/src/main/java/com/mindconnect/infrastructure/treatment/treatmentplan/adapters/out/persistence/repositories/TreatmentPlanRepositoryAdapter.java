package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.repositories;

import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.entity.TreatmentPlanJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.mappers.TreatmentPlanPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TreatmentPlanRepositoryAdapter implements TreatmentPlanRepository {

    private final TreatmentPlanJpaRepository jpaRepository;
    private final TreatmentPlanPersistenceMapper mapper;

    public TreatmentPlanRepositoryAdapter(TreatmentPlanJpaRepository jpaRepository, TreatmentPlanPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TreatmentPlan save(TreatmentPlan treatmentPlan) {
        TreatmentPlanJpaEntity entity = mapper.toJpa(treatmentPlan);
        TreatmentPlanJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<TreatmentPlan> findById(TreatmentPlanId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TreatmentPlan> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(TreatmentPlan treatmentPlan) {
        jpaRepository.deleteById(treatmentPlan.id().value());
    }

    public List<TreatmentPlan> findByEncounterId(UUID encounterId) {
        return jpaRepository.findByEncounterId(encounterId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<TreatmentPlan> findByProfessionalId(UUID professionalId) {
        return jpaRepository.findByProfessionalId(professionalId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<TreatmentPlan> findByTreatmentStatusId(UUID treatmentStatusId) {
        return jpaRepository.findByTreatmentStatusId(treatmentStatusId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}