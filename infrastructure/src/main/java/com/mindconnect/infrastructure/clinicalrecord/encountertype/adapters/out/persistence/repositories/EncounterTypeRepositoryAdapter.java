package com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encountertype.model.aggregate.EncounterType;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountertype.port.repository.EncounterTypeRepository;
import com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.mappers.EncounterTypePersistenceMapper;

public class EncounterTypeRepositoryAdapter implements EncounterTypeRepository {

    private final EncounterTypeJpaRepository jpaRepository;
    private final EncounterTypePersistenceMapper mapper;

    public EncounterTypeRepositoryAdapter(
            EncounterTypeJpaRepository jpaRepository,
            EncounterTypePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public EncounterType save(EncounterType encounterType) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(encounterType)));
    }

    @Override
    public Optional<EncounterType> findById(EncounterTypeId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<EncounterType> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, EncounterTypeId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(EncounterType encounterType) {
        jpaRepository.deleteById(encounterType.id().value());
    }
}