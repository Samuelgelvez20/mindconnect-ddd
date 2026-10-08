package com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;

public class StateRegionRepositoryAdapter implements StateRegionRepository {

    private final StateRegionJpaRepository stateRegionJpaRepository;
    private final StateRegionPersistenceMapper mapper;

    public StateRegionRepositoryAdapter(
            StateRegionJpaRepository stateRegionJpaRepository,
            StateRegionPersistenceMapper mapper) {
        this.stateRegionJpaRepository = stateRegionJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public StateRegion save(StateRegion stateRegion) {
        return mapper.toDomain(stateRegionJpaRepository.save(mapper.toJpa(stateRegion)));
    }

    @Override
    public Optional<StateRegion> findById(StateRegionId id) {
        return stateRegionJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<StateRegion> findAll() {
        return stateRegionJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCountryIdAndCode(CountryId countryId, String code) {
        return stateRegionJpaRepository.existsByCountryIdAndCode(countryId.value(), code);
    }

    @Override
    public boolean existsByCountryIdAndCodeAndIdNot(CountryId countryId, String code, StateRegionId id) {
        return stateRegionJpaRepository.existsByCountryIdAndCodeAndIdNot(countryId.value(), code, id.value());
    }

    @Override
    public void delete(StateRegion stateRegion) {
        stateRegionJpaRepository.deleteById(stateRegion.id().value());
    }
}