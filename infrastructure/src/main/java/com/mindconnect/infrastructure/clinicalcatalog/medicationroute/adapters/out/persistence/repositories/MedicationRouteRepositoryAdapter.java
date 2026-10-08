package com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute;
import com.mindconnect.domain.clinicalcatalog.medicationroute.model.valueobject.MedicationRouteId;
import com.mindconnect.domain.clinicalcatalog.medicationroute.port.repository.MedicationRouteRepository;
import com.mindconnect.infrastructure.clinicalcatalog.medicationroute.adapters.out.persistence.mappers.MedicationRoutePersistenceMapper;

public class MedicationRouteRepositoryAdapter implements MedicationRouteRepository {

    private final MedicationRouteJpaRepository jpaRepository;
    private final MedicationRoutePersistenceMapper mapper;

    public MedicationRouteRepositoryAdapter(
            MedicationRouteJpaRepository jpaRepository,
            MedicationRoutePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute save(com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute medicationRoute) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(medicationRoute)));
    }

    @Override
    public Optional<com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute> findById(MedicationRouteId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute> findByCode(String code) {
        return jpaRepository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, MedicationRouteId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(com.mindconnect.domain.clinicalcatalog.medicationroute.model.aggregate.MedicationRoute medicationRoute) {
        jpaRepository.deleteById(medicationRoute.id().value());
    }
}