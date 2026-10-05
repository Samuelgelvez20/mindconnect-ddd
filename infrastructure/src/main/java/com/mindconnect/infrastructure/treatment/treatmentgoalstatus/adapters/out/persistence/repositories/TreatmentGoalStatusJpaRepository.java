package com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.mindconnect.domain.treatment.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.mindconnect.domain.treatment.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.entity.TreatmentGoalStatusJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.mappers.TreatmentGoalStatusPersistenceMapper;

public class TreatmentGoalStatusJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final TreatmentGoalStatusPersistenceMapper mapper;

    public TreatmentGoalStatusJpaRepository() {
        this.mapper = new TreatmentGoalStatusPersistenceMapper();
    }

    public TreatmentGoalStatus save(TreatmentGoalStatus treatmentGoalStatus) {
        TreatmentGoalStatusJpaEntity jpa = mapper.toJpa(treatmentGoalStatus);
        entityManager.persist(jpa);
        return mapper.toDomain(jpa);
    }

    public Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id) {
        TreatmentGoalStatusJpaEntity jpa = entityManager.find(TreatmentGoalStatusJpaEntity.class, id.value());
        if (jpa == null) {
            return Optional.empty();
        }
        return Optional.of(mapper.toDomain(jpa));
    }

    public List<TreatmentGoalStatus> findAll() {
        TypedQuery<TreatmentGoalStatusJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM TreatmentGoalStatusJpaEntity t", TreatmentGoalStatusJpaEntity.class);
        return query.getResultStream()
                .map(mapper::toDomain)
                .toList();
    }

    public void delete(TreatmentGoalStatus treatmentGoalStatus) {
        TreatmentGoalStatusJpaEntity jpa = entityManager.getReference(TreatmentGoalStatusJpaEntity.class, treatmentGoalStatus.id().value());
        entityManager.remove(jpa);
    }
}