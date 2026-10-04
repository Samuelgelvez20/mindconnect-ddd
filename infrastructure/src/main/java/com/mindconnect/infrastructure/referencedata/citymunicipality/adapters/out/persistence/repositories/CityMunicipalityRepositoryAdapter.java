package com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;

public class CityMunicipalityRepositoryAdapter implements CityMunicipalityRepository {

    private final CityMunicipalityJpaRepository cityMunicipalityJpaRepository;
    private final CityMunicipalityPersistenceMapper mapper;

    public CityMunicipalityRepositoryAdapter(
            CityMunicipalityJpaRepository cityMunicipalityJpaRepository,
            CityMunicipalityPersistenceMapper mapper) {
        this.cityMunicipalityJpaRepository = cityMunicipalityJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CityMunicipality save(CityMunicipality cityMunicipality) {
        return mapper.toDomain(cityMunicipalityJpaRepository.save(mapper.toJpa(cityMunicipality)));
    }

    @Override
    public Optional<CityMunicipality> findById(CityMunicipalityId id) {
        return cityMunicipalityJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<CityMunicipality> findAll() {
        return cityMunicipalityJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByRegionIdAndCode(StateRegionId regionId, String code) {
        return cityMunicipalityJpaRepository.existsByRegionIdAndCode(regionId.value(), code);
    }

    @Override
    public boolean existsByRegionIdAndCodeAndIdNot(StateRegionId regionId, String code, CityMunicipalityId id) {
        return cityMunicipalityJpaRepository.existsByRegionIdAndCodeAndIdNot(regionId.value(), code, id.value());
    }

    @Override
    public void delete(CityMunicipality cityMunicipality) {
        cityMunicipalityJpaRepository.deleteById(cityMunicipality.id().value());
    }
}