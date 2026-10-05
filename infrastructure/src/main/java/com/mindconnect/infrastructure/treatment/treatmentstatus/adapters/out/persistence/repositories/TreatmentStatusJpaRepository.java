package com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.entity.TreatmentStatusJpaEntity;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.mappers.TreatmentStatusPersistenceMapper;

public class TreatmentStatusJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final TreatmentStatusPersistenceMapper mapper;

    public TreatmentStatusJpaRepository() {
        this.mapper = new TreatmentStatusPersistenceMapper();
    }

    public TreatmentStatus save(TreatmentStatus treatmentStatus) {
        TreatmentStatusJpaEntity jpa = mapper.toJpa(treatmentStatus);
        entityManager.persist(jpa);
        return mapper.toDomain(jpa);
    }

    public Optional<TreatmentStatus> findById(TreatmentStatusId id) {
        TreatmentStatusJpaEntity jpa = entityManager.find(TreatmentStatusJpaEntity.class, id.value());
        if (jpa == null) {
            return Optional.empty();
        }
        return Optional.of(mapper.toDomain(jpa));
    }

    public List<TreatmentStatus> findAll() {
        TypedQuery<TreatmentStatusJpaEntity> query = entityManager.createQuery(
                "SELECT t FROM TreatmentStatusJpaEntity t", TreatmentStatusJpaEntity.class);
        return query.getResultStream()
                .map(mapper::toDomain)
                .toList();
    }

    public void delete(TreatmentStatus treatmentStatus) {
        TreatmentStatusJpaEntity jpa = entityManager.getReference(TreatmentStatusJpaEntity.class, treatmentStatus.id().value());
        entityManager.remove(jpa);
    }
}