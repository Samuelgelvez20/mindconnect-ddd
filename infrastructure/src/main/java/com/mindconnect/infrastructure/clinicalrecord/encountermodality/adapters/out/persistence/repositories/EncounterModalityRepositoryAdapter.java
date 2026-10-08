package com.mindconnect.infrastructure.clinicalrecord.encountermodality.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate.EncounterModality;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;
import com.mindconnect.infrastructure.clinicalrecord.encountermodality.adapters.out.persistence.mappers.EncounterModalityPersistenceMapper;

public class EncounterModalityRepositoryAdapter implements EncounterModalityRepository {

    private final EncounterModalityJpaRepository jpaRepository;
    private final EncounterModalityPersistenceMapper mapper;

    public EncounterModalityRepositoryAdapter(
            EncounterModalityJpaRepository jpaRepository,
            EncounterModalityPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public EncounterModality save(EncounterModality encounterModality) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(encounterModality)));
    }

    @Override
    public Optional<EncounterModality> findById(EncounterModalityId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<EncounterModality> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, EncounterModalityId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(EncounterModality encounterModality) {
        jpaRepository.deleteById(encounterModality.id().value());
    }
}