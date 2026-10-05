package com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.entity.TreatmentPlanJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.mappers.TreatmentPlanPersistenceMapper;

public class TreatmentPlanJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final TreatmentPlanPersistenceMapper mapper;

    public TreatmentPlanJpaRepository() {
        this.mapper = new TreatmentPlanPersistenceMapper();
    }

    public TreatmentPlan save(TreatmentPlan treatmentPlan) {
        TreatmentPlanJpaEntity jpa = mapper.toJpa(treatmentPlan);
        entityManager.persist(jpa);
        return mapper.toDomain(jpa);
    }

    public Optional<TreatmentPlan> findById(TreatmentPlanId id) {
        TreatmentPlanJpaEntity jpa = entityManager.find(TreatmentPlanJpaEntity.class, id.value());
        if (jpa == null) {
            return Optional.empty();
        }
        return Optional.of(mapper.toDomain(jpa));
    }

    public List<TreatmentPlan> findAll() {
        TypedQuery<TreatmentPlanJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM TreatmentPlanJpaEntity t", TreatmentPlanJpaEntity.class);
        return query.getResultStream()
                .map(mapper::toDomain)
                .toList();
    }

    public void delete(TreatmentPlan treatmentPlan) {
        TreatmentPlanJpaEntity jpa = entityManager.getReference(TreatmentPlanJpaEntity.class, treatmentPlan.id().value());
        entityManager.remove(jpa);
    }
}