package com.mindconnect.domain.referencedata.country.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public interface CountryRepository {

    Country save(Country country);

    Optional<Country> findById(CountryId id);

    List<Country> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, CountryId id);

    void delete(Country country);
}
