package com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;
import com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.mappers.CountryPersistenceMapper;

public class CountryRepositoryAdapter implements CountryRepository {

    private final CountryJpaRepository countryJpaRepository;
    private final CountryPersistenceMapper mapper;

    public CountryRepositoryAdapter(
            CountryJpaRepository countryJpaRepository,
            CountryPersistenceMapper mapper) {
        this.countryJpaRepository = countryJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Country save(Country country) {
        return mapper.toDomain(countryJpaRepository.save(mapper.toJpa(country)));
    }

    @Override
    public Optional<Country> findById(CountryId id) {
        return countryJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Country> findAll() {
        return countryJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return countryJpaRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, CountryId id) {
        return countryJpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(Country country) {
        countryJpaRepository.deleteById(country.id().value());
    }
}
