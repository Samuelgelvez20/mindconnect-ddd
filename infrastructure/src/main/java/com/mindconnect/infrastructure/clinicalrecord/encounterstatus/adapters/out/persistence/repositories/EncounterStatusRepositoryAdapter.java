package com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encounterstatus.model.aggregate.EncounterStatus;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;
import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.mappers.EncounterStatusPersistenceMapper;

public class EncounterStatusRepositoryAdapter implements EncounterStatusRepository {

    private final EncounterStatusJpaRepository jpaRepository;
    private final EncounterStatusPersistenceMapper mapper;

    public EncounterStatusRepositoryAdapter(
            EncounterStatusJpaRepository jpaRepository,
            EncounterStatusPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public EncounterStatus save(EncounterStatus encounterStatus) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(encounterStatus)));
    }

    @Override
    public Optional<EncounterStatus> findById(EncounterStatusId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<EncounterStatus> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, EncounterStatusId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, EncounterStatusId id) {
        return jpaRepository.existsByNameAndIdNot(name, id.value());
    }

    @Override
    public void delete(EncounterStatus encounterStatus) {
        jpaRepository.deleteById(encounterStatus.id().value());
    }
}