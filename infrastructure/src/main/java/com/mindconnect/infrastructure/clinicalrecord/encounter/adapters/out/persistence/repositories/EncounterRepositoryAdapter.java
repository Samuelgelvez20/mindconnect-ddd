package com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;
import com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.mappers.EncounterPersistenceMapper;

public class EncounterRepositoryAdapter implements EncounterRepository {

    private final EncounterJpaRepository jpaRepository;
    private final EncounterPersistenceMapper mapper;

    public EncounterRepositoryAdapter(
            EncounterJpaRepository jpaRepository,
            EncounterPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Encounter save(Encounter encounter) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(encounter)));
    }

    @Override
    public Optional<Encounter> findById(EncounterId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Encounter> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Encounter> findByClinicalRecordId(ClinicalRecordId clinicalRecordId) {
        return jpaRepository.findByClinicalRecordId(clinicalRecordId.value()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Encounter encounter) {
        jpaRepository.deleteById(encounter.id().value());
    }
}